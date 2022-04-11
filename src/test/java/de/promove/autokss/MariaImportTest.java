package de.promove.autokss;


import de.promove.autokss.model.*;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.service.InitDBService;
import de.promove.autokss.service.UserService;
import de.promove.autokss.web.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest
@ActiveProfiles({"dbimport"})
public class MariaImportTest {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private GenericService genericService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private InitDBService initDBService;

    private Map<Integer, Bereich> bereicheMap = new HashMap<>();
    private Map<Integer, User> usersMap = new HashMap<>();
    private Map<Integer, Einsatzkonzentration> einsatzkonzentrationMap = new HashMap<>();
    private Map<Integer, Maschine> maschinenMap = new HashMap<>();

    @Test
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
        logger.info("Persisted Bereiche");

        initDBService.createAdminAccount();
        logger.info("Persisted Admin Account");
        List<User> users = jdbcTemplate.query("select * from neutblPrüfer", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                String vorname = rs.getString("Vorname");
                String nachname = rs.getString("Nachname");
                User user = new User(vorname + "." + nachname + "@promove-gmbh.de", passwordEncoder.encode("passwort"),
                        vorname, nachname, rs.getString("Bemerkung"), Role.USER);
                usersMap.put(rs.getInt("IDPrüfer"), user);
                return user;
            }
        });
        genericService.persistAll(users);
        logger.info("Persisted Users");

        List<Einsatzkonzentration> einsatzkonzentrationen = jdbcTemplate.query("select * from neutblEinsatzkonzentration", new RowMapper<Einsatzkonzentration>() {
            @Override
            public Einsatzkonzentration mapRow(ResultSet rs, int rowNum) throws SQLException {
                Einsatzkonzentration e = new Einsatzkonzentration(String.valueOf(rowNum), rs.getInt("Min"),
                        rs.getInt("Max"), rs.getDouble("Soll"));
                einsatzkonzentrationMap.put(rs.getInt("ID"), e);
                return e;
            }
        });
        genericService.persistAll(einsatzkonzentrationen);
        logger.info("Persisted Einsatzkonzentration");

        Kuehlschmierstoff kss = new Kuehlschmierstoff("Zubora 65 H-Ultra", "Viscotex", 8.7, 9.3, 1.3, 16.0, 0.01, 14.4, null, null, null);
        genericService.persist(kss);

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
                m.setKuehlschmierstoff(kss);

                maschinenMap.put(rs.getInt("IDMaschine"), m);
                return m;
            }
        });
        genericService.persistAll(maschinen);
        logger.info("Persisted Maschine");

        List<Messung> messungen = jdbcTemplate.query("select * from neutblMessung", new RowMapper<Messung>() {
            @Override
            public Messung mapRow(ResultSet rs, int rowNum) throws SQLException {
                Messung m = new Messung(rs.getDate("Prüfdatum"), usersMap.get(rs.getInt("Prüfer")),
                        maschinenMap.get(rs.getInt("Maschine")));
                m.setTimestamp(rs.getDate("Timestamp"));
                m.setRefraktometerKonzProz(rs.getDouble("RefraktometerKonzProz"));
                m.setRefraktometerKonzProz2(rs.getDouble("RefraktometerKonz%Mess2"));
                m.setNitrit(rs.getDouble("Nitrit"));
                m.setNitrit2(rs.getDouble("NitritMess2"));
                m.setPh(rs.getDouble("pHWert"));
                m.setPh2(rs.getDouble("pHWertMess2"));
                m.setOelNachgefuellt(rs.getDouble("ÖlNachgefüllt"));
                m.setWasserNachgefuellt(rs.getDouble("WasserNachgefüllt"));
                m.setWasserstandCm(rs.getDouble("Wasserstandcm"));
                m.setLocked(rs.getBoolean("gesperrt"));
                m.setBemerkung(rs.getString("Bemerkung"));
                return m;
            }
        });
        genericService.persistAll(messungen);
        logger.info("Persisted Messung");
    }
}
