package de.promove.autokss.web.crud;

import de.promove.autokss.model.User;
import de.promove.autokss.service.UserService;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import de.promove.autokss.web.util.GrowlMessenger;
import de.promove.autokss.web.util.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.faces.view.ViewScoped;

@Controller
@ViewScoped
public class UserBean extends AbstractCrudBean<User> {

    private User passwordUser;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserBean() {
        super(User.class);
    }

    @Override
    protected void persistEditItem(User editItem) {
        userService.persist(editItem);
    }

    @Override
    protected void mergeEditItem(User editItem) {
        userService.merge(editItem);
    }

    public void editPassword() {
        this.passwordUser = editItem;
    }

    public User getPasswordUser() {
        return passwordUser;
    }
}
