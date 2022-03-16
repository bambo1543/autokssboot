package de.promove.autokss;

import de.promove.autokss.dao.GenericDao;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.dao.QueryParameterEntry;
import de.promove.autokss.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@SpringBootTest
class AutoKssApplicationTests {

	@Autowired
	GenericDao genericDao;

	@Test
	void contextLoads() {
	}

	@Test
	public void testUser() {
		User u1 = new User("Andreas", "Baumgartner", "andreas.bga@gmail.com", "");
		genericDao.persist(u1);
		User u2 = new User("Markus", "Vogel", "mvogel@promove-gmbh.de", "");
		genericDao.persist(u2);

		List<User> users = genericDao.list(User.class, QueryParameter.with("firstName", QueryParameterEntry.Operator.STARTS, "Andre") );
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
		User u1 = new User(null, "Baumgartner", "andreas@bga@gmail.com", "");
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

		Einsatzkonzentration e1 = new Einsatzkonzentration(5.0, 8.0, 6.5);
		genericDao.persist(e1);

		Einsatzkonzentration e2 = new Einsatzkonzentration(7.0, 8.0, 7.5);
		genericDao.persist(e2);

		Maschine m1 = new Maschine("CTX 310", b3);
		m1.setLetzterEmulsionswechsel(new Date());
		m1.setEinsatzkonzentration(e1);
		genericDao.persist(m1);
		Maschine m2 = new Maschine("DMC 64 V", b2);
		m2.setEinsatzkonzentration(e1);
		genericDao.persist(m2);
		Maschine m3 = new Maschine("Meba Bandsäge", b1);
		m3.setEinsatzkonzentration(e2);
		genericDao.persist(m3);

		List<Maschine> maschinen = genericDao.listAll(Maschine.class);
		Assert.isTrue(maschinen.size() == 3);

		Maschine byId = genericDao.findById(Maschine.class, m1.getId());
		Assert.isTrue(b3.equals(byId.getBereich()));
		Assert.isTrue(byId.getLetzterEmulsionswechsel().before(new Date()));

		User u1 = new User("Andreas", "Baumgartner", "andreas.bga@gmail.com", "");
		genericDao.persist(u1);
		User u2 = new User("Markus", "Vogel", "mvogel@promove-gmbh.de", "");
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

}
