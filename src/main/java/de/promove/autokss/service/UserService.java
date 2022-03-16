package de.promove.autokss.service;

import de.promove.autokss.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private GenericService genericService;

    public List<User> listAllUsers() {
        return genericService.listAll(User.class);
    }

}