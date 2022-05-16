package de.promove.autokss.service;

import de.promove.autokss.configuration.EmailConfiguration;
import de.promove.autokss.dao.PersistentPropertiesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

@Service
@Transactional
public class PersistentPropertiesService {

    @Autowired
    private EmailConfiguration emailConfiguration;

    @Autowired
    private PersistentPropertiesDao dao;

    public Properties loadProperties(String prefix) {
        return dao.loadProperties(prefix);
    }

    public void persistProperties(Properties properties) {
        dao.persistProperties(properties);
        emailConfiguration.updateJavaMailSender();
    }
}
