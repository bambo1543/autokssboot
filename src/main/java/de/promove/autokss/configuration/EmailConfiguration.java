package de.promove.autokss.configuration;

import de.promove.autokss.dao.PersistentPropertiesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Autowired
    private PersistentPropertiesDao dao;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private JavaMailSenderImpl mailSender;

    @Bean
    public JavaMailSender getJavaMailSender() {
        if(mailSender == null) {
            mailSender = new JavaMailSenderImpl();
            updateJavaMailSender();

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
        }

        return mailSender;
    }

    public void updateJavaMailSender() {
        Properties properties = dao.loadProperties("mail");

        mailSender.setHost(properties.getProperty("mail.host", host));
        mailSender.setPort(Integer.parseInt(properties.getProperty("mail.port", String.valueOf(port))));
        mailSender.setUsername(properties.getProperty("mail.username", username));
        mailSender.setPassword(properties.getProperty("mail.password", password));
    }
}
