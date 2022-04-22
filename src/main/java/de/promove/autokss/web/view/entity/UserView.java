package de.promove.autokss.web.view.entity;

import de.promove.autokss.model.Role;
import de.promove.autokss.model.User;
import de.promove.autokss.service.UserService;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.FacesUtils;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
@ViewScope
public class UserView extends AbstractCrudView<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    public UserView() {
        super(User.class);
    }

    @Override
    protected void persistEditItem(User editItem) {
        userService.persistAndEncode(editItem);
    }

    public void editPassword() {
        this.editItem = selectedItem;
        this.visibleSection = "password";
    }

    public void savePassword() {
        userService.mergeAndEncode(editItem);
        editItem = null;

        this.visibleSection = "table";
    }

    public void cancelPassword() {
        editItem = null;
        this.visibleSection = "table";
    }

    public void setSelectedItem(User selectedItem) {
        this.selectedItem = selectedItem;
        String remoteUser = FacesUtils.getRemoteUsername();
        UserDetails userDetails = userDetailsService.loadUserByUsername(remoteUser);
        modifiable = userDetails.equals(selectedItem) || userDetails.getAuthorities().contains(Role.ADMIN);
    }

    public String getLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale().getCountry();
    }

}
