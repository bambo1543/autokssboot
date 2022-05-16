package de.promove.autokss;

import de.promove.autokss.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
