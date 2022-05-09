package de.promove.autokss.configuration;

import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class FlywayJndiConfiguration {

    @Bean
    @FlywayDataSource
    public DataSource getFlywayDataSource() throws NamingException {
        return new JndiTemplate().lookup("java:comp/env/jdbc/AutoKSS", DataSource.class);
    }
}
