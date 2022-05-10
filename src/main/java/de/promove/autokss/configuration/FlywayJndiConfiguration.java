package de.promove.autokss.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@Profile({"mariadb, jndi-mariadb"})
public class FlywayJndiConfiguration {

    @Value("spring.datasource.jndi-name")
    private String jndiName;

    @Bean
    @FlywayDataSource
    public DataSource getFlywayDataSource() throws NamingException {
        return new JndiTemplate().lookup("java:comp/env/" + jndiName, DataSource.class);
    }
}
