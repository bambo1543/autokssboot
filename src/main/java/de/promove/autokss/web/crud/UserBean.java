package de.promove.autokss.web.crud;

import de.promove.autokss.model.User;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.view.ViewScoped;

@Controller
@ViewScoped
public class UserBean extends AbstractCrudBean<User> {

    public UserBean() {
        super(User.class);
    }
}
