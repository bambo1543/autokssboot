package de.promove.autokss;


import de.promove.autokss.model.*;
import de.promove.autokss.service.GenericService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles({"dbimport"})
public class MariaImportTest {

    @Autowired
    private GenericService genericService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<Integer, Bereich> bereicheMap = new HashMap<>();
    private Map<Integer, User> usersMap = new HashMap<>();
    private Map<Integer, Einsatzkonzentration> einsatzkonzentrationMap = new HashMap<>();
    private Map<Integer, Maschine> maschinenMap = new HashMap<>();

//    @Test
    public void test() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://autokss.gftn.h-da.de:3306/autoKSS_DB_PM");
        dataSource.setUsername("promove");
        dataSource.setPassword("ProMove#73092");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Bereich> bereiche = jdbcTemplate.query("select * from neutblBereich", new RowMapper<Bereich>() {
            @Override
            public Bereich mapRow(ResultSet rs, int rowNum) throws SQLException {
                Bereich bereich = new Bereich(rs.getString("Bereich"));
                bereicheMap.put(rs.getInt("ID"), bereich);
                return bereich;
            }
        });
        genericService.persistAll(bereiche);

        List<User> users = jdbcTemplate.query("select * from neutblPrüfer", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                String vorname = rs.getString("Vorname");
                String nachname = rs.getString("Nachname");
                User user = new User(vorname + "." + nachname + "@promove-gmbh.de", passwordEncoder.encode("passwort"), vorname, nachname, rs.getString("Bemerkung"));
                usersMap.put(rs.getInt("IDPrüfer"), user);
                return user;
            }
        });
        genericService.persistAll(users);

        List<Einsatzkonzentration> einsatzkonzentrationen = jdbcTemplate.query("select * from neutblEinsatzkonzentration", new RowMapper<Einsatzkonzentration>() {
            @Override
            public Einsatzkonzentration mapRow(ResultSet rs, int rowNum) throws SQLException {
                Einsatzkonzentration e = new Einsatzkonzentration(rs.getInt("Min"), rs.getInt("Max"), rs.getDouble("Soll"));
                einsatzkonzentrationMap.put(rs.getInt("ID"), e);
                return e;
            }
        });
        genericService.persistAll(einsatzkonzentrationen);

        List<Maschine> maschinen = jdbcTemplate.query("select * from neutblMaschine", new RowMapper<Maschine>() {
            @Override
            public Maschine mapRow(ResultSet rs, int rowNum) throws SQLException {
                Maschine m = new Maschine(rs.getString("Bezeichnung"), rs.getString("Bemerkung"),
                        bereicheMap.get(rs.getInt("Bereich")));
                m.setTankVolumenLiter(rs.getDouble("VolumenTank"));
                m.setWasserstandMaxCm(rs.getDouble("maxWassercm"));
                m.setWasserstandMinCm(0.0D);
                m.setCmEntsprichtLiter(0.0D);
                m.setLetzterEmulsionswechsel(rs.getDate("LetzterEmulsionswechsel"));
                m.setEinsatzkonzentration(einsatzkonzentrationMap.get(rs.getInt("Einsatzkonzentration")));

                maschinenMap.put(rs.getInt("IDMaschine"), m);
                return m;
            }
        });
        genericService.persistAll(maschinen);

//        List<Messung> messungen = jdbcTemplate.query("select * from neutblMessung", new RowMapper<Messung>() {
//            @Override
//            public Messung mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Messung m = new Messung(rs.getDate("Prüfdatum"), usersMap.get(rs.getInt("Prüfer")),
//                        maschinenMap.get(rs.getInt("Maschine")));
//                m.setTimestamp(rs.getDate("Timestamp"));
////                m.setRefraktometerKonzProz();
//                return m;
//            }
//        });
    }
}
