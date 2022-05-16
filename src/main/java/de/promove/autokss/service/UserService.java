package de.promove.autokss.service;

import de.promove.autokss.dao.generic.QueryParameter;
import de.promove.autokss.model.User;
import de.promove.autokss.model.User_;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService extends AbstractEntityService<User> {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        super(User.class);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void persistAndEncode(User user) {
        encodePassword(user);
        super.persist(user);
    }

    public User mergeAndEncode(User user) {
        encodePassword(user);
        return super.merge(user);
    }

    private void encodePassword(User user) {
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
    }

    public User findUserByUsername(String username) {
        return find(QueryParameter.with(User_.email, username));
    }
}