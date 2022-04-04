package de.promove.autokss.service;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.model.User;
import de.promove.autokss.web.converter.BereichConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

//@Service
//@Transactional
public class InitDBService {

    Logger logger = LoggerFactory.getLogger(InitDBService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private GenericService genericService;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        if(!environment.acceptsProfiles(Profiles.of("test"))) {
            Long count = genericService.count(User.class, null, null);
            if(count == 0) {
//                String password = UUID.randomUUID().toString();
                String password = "password";
                User user = new User("admin@mail.com", password, "Admin", "Admin", "");
                userService.persist(user);
                logger.warn("User 'admin@mail.com' with password '" + password + "' created.");
            }

            Bereich b1 = new Bereich("Sägen");
            genericService.persist(b1);
            Bereich b2 = new Bereich("Fräsen");
            genericService.persist(b2);
            Bereich b3 = new Bereich("Drehen");
            genericService.persist(b3);
        }
    }

}
