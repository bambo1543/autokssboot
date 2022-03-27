package de.promove.autokss.service;

import de.promove.autokss.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService extends AbstractEntityService<User> {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        super(User.class);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void persist(User editItem) {
        encodeEditItem(editItem);
        super.persist(editItem);
    }

    @Override
    public User merge(User editItem) {
//        encodeEditItem(editItem);
        return super.merge(editItem);
    }

    private void encodeEditItem(User editItem) {
        String encoded = passwordEncoder.encode(editItem.getPassword());
        editItem.setPassword(encoded);
    }

}