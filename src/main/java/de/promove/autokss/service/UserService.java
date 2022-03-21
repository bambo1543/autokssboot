package de.promove.autokss.service;

import de.promove.autokss.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class UserService extends EntityService<User> {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        super(User.class);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        if(!environment.acceptsProfiles(Profiles.of("test"))) {
            Long count = count();
            if(count == 0) {
                String password = UUID.randomUUID().toString();
                User user = new User("admin@mail.com", password, "Admin", "Admin", "");
                persist(user);
                logger.warn("User 'admin@mail.com' with password '" + password + "' created.");
            }
        }
    }

    @Override
    public void persist(User editItem) {
        encodeEditItem(editItem);
        super.persist(editItem);
    }

    @Override
    public User merge(User editItem) {
        encodeEditItem(editItem);
        return super.merge(editItem);
    }

    private void encodeEditItem(User editItem) {
        String encoded = passwordEncoder.encode(editItem.getPassword());
        editItem.setPassword(encoded);
    }

}