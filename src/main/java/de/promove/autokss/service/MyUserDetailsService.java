package de.promove.autokss.service;

import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.Role;
import de.promove.autokss.model.User;
import de.promove.autokss.model.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsManager, UserDetailsPasswordService {

    @Autowired
    private GenericService genericService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loadUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with email '" + username + "' not found");
        }
        return user;
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = loadUserByEmail(userDetails.getUsername());
        if(user != null) {
            user.setPassword(newPassword);
            genericService.merge(user);
        }
        return user;
    }

    private User loadUserByEmail(String userDetails) {
        return genericService.find(User.class, QueryParameter.with(User_.email, userDetails));
    }

    @Override
    public void createUser(UserDetails userDetails) {
        User user = new User(userDetails.getUsername(), userDetails.getPassword(), "", "", "", (Role) userDetails.getAuthorities().iterator().next());
        genericService.persist(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        User user = loadUserByEmail(userDetails.getUsername());
        user.getUsername();
    }

    @Override
    public void deleteUser(String username) {
        User user = loadUserByEmail(username);
        if(user != null) {
            genericService.remove(user);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {}

    @Override
    public boolean userExists(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return userDetails != null;
    }
}
