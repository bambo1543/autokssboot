package de.promove.autokss;

import de.promove.autokss.dao.GenericDao;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.dao.QueryParameterEntry;
import de.promove.autokss.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"test"})
class GenericDaoTests {

	@Autowired
	GenericDao genericDao;

	private static User createMarkus() {
		return new User("mvogel@promove-gmbh.de", "password", "Markus", "Vogel", "");
	}

	private static User createAndreas() {
		return new User("andreas.bga@gmail.com", "password", "Andreas", "Baumgartner", "");
	}

	@Test
	public void testUser() {
		User u1 = createAndreas();
		genericDao.persist(u1);
		User u2 = createMarkus();
		genericDao.persist(u2);

		List<User> users = genericDao.list(User.class, QueryParameter.with(User_.firstName, QueryParameterEntry.Operator.STARTS, "Andre") );
		Assert.isTrue(users.size() == 1);

		users = genericDao.listAll(User.class);
		org.springframework.util.Assert.isTrue(users.size() == 2);
		Assert.isTrue(users.contains(u1));
		Assert.isTrue(users.contains(u2));

		for (User user : users) {
			genericDao.remove(user);
		}

		users = genericDao.listAll(User.class);
		Assert.isTrue(users.isEmpty());
	}

	@Test
	public void testUserInvalidEmail() {
		User u1 = createAndreas();
		u1.setEmail("Invalid@Email@Format");
		try {
			genericDao.persist(u1);
			Assert.isTrue(false, "Exception expected");
		} catch (TransactionSystemException ignore) {}
	}

	@Test
	public void testMaschinenUndBereich() {
		Bereich b1 = new Bereich("Sägen");
		genericDao.persist(b1);
		Bereich b2 = new Bereich("Fräsen");
		genericDao.persist(b2);
		Bereich b3 = new Bereich("Drehen");
		genericDao.persist(b3);

		Einsatzkonzentration e1 = new Einsatzkonzentration("Stufe1", 5.0, 8.0, 6.5);
		genericDao.persist(e1);

		Einsatzkonzentration e2 = new Einsatzkonzentration("Stufe2", 7.0, 8.0, 7.5);
		genericDao.persist(e2);

		Maschine m1 = new Maschine("CTX 310", "test", b3);
		applyNotNullAttributes(m1);
		m1.setLetzterEmulsionswechsel(new Date(new Date().getTime() - 1000));
		m1.setEinsatzkonzentration(e1);
		genericDao.persist(m1);
		Maschine m2 = new Maschine("DMC 64 V","test", b2);
		applyNotNullAttributes(m2);
		m2.setEinsatzkonzentration(e1);
		genericDao.persist(m2);
		Maschine m3 = new Maschine("Meba Bandsäge", "test", b1);
		applyNotNullAttributes(m3);
		m3.setEinsatzkonzentration(e2);
		genericDao.persist(m3);

		List<Maschine> maschinen = genericDao.listAll(Maschine.class);
		Assert.isTrue(maschinen.size() == 3);

		Maschine byId = genericDao.findById(Maschine.class, m1.getId());
		Assert.isTrue(b3.equals(byId.getBereich()));
		Assert.isTrue(byId.getLetzterEmulsionswechsel().before(new Date()));

		User u1 = createAndreas();
		genericDao.persist(u1);
		User u2 = createMarkus();
		genericDao.persist(u2);

		Messung me1 = new Messung(new Date(), u1, m1);
		me1.setTimestamp(new Date());

		me1.setPh(9.0);
		me1.setNitrit(5.0);
		genericDao.persist(me1);

		List<Messung> messungen = genericDao.listAll(Messung.class);
		Assert.isTrue(messungen.size() == 1);

		Messung me2 = new Messung(new Date(), u1, m1);
		me2.setTimestamp(new Date());

		me2.setPh(15.0);
		me2.setNitrit(5.0);

		try {
			genericDao.persist(me2);
			Assert.isTrue(false, "Exception expected");
		} catch (TransactionSystemException ignore) {}
	}

	private void applyNotNullAttributes(Maschine maschine) {
		maschine.setCmEntsprichtLiter(1.0);
		maschine.setTankVolumenLiter(1.0);
		maschine.setWasserstandMinCm(1.0);
		maschine.setWasserstandMaxCm(1.0);
	}


}
