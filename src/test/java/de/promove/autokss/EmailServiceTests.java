package de.promove.autokss;

import de.promove.autokss.dao.GenericDao;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.dao.QueryParameterEntry;
import de.promove.autokss.model.*;
import de.promove.autokss.service.EmailService;
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
class EmailServiceTests {

	@Autowired
	EmailService emailService;

//	@Test
	public void testSimpleEmail() {
		emailService.sendSimpleMessage("andreas.bga@gmail.com", "Test AutoKSS", "Test AutoKSS");
	}

}
