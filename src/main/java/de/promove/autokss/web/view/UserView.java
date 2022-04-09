package de.promove.autokss.web.view;

import de.promove.autokss.model.User;
import de.promove.autokss.service.UserService;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class UserView extends AbstractCrudView<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserView() {
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
        this.editItem = selectedItem;

        this.visibleSection = "password";
    }

    public void savePassword() {
        String encoded = passwordEncoder.encode(editItem.getPassword());
        editItem.setPassword(encoded);
        userService.merge(editItem);
        editItem = null;

        this.visibleSection = "table";
    }

    public void cancelPassword() {
        editItem = null;

        this.visibleSection = "table";
    }

    public String getLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale().getCountry();
    }

}
