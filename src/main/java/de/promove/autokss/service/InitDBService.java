package de.promove.autokss.service;

import de.promove.autokss.model.Role;
import de.promove.autokss.model.User;
import de.promove.autokss.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
@Transactional
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
        if (!environment.acceptsProfiles(Profiles.of("test"))) {
            createAdminAccount();
        }
    }

    public void createAdminAccount() {
        Long count = genericService.count(User.class, null, null);
        if (count == 0) {
            String password = "changeit";
            User user = new User("admin@mail.com", password, "Admin", "Admin", "", true, true, DateUtils.future(), DateUtils.future(), Role.ADMIN);
            userService.persist(user);
            logger.warn("User 'admin@mail.com' with password '" + password + "' created.");
        }
    }

}
