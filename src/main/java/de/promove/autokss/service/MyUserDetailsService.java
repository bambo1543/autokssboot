package de.promove.autokss.service;

import de.promove.autokss.dao.GenericDao;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.User;
import de.promove.autokss.model.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsManager, UserDetailsPasswordService {

    @Autowired
    private GenericDao genericDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = genericDao.find(User.class, QueryParameter.with(User_.email, username));
        if (user == null) {
            throw new UsernameNotFoundException("User with email '" + username + "' not found");
        }
        return user;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        user.getUsername();
    }

    @Override
    public void updateUser(UserDetails user) {
        user.getUsername();
    }

    @Override
    public void deleteUser(String username) {
        username.toString();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        oldPassword.toString();
    }

    @Override
    public boolean userExists(String username) {
        return true;
    }
}
