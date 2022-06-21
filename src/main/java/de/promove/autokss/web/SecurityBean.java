package de.promove.autokss.web;

import de.promove.autokss.model.Role;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

@Controller
@SessionScope
public class SecurityBean implements Serializable {

    private static final long serialVersionUID = 1234568L;

    @Getter
    @Autowired
    private BuildProperties buildProperties;

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !"anonymousUser".equals(authentication.getPrincipal());
    }

    public String getBuildDate() {
        return DateFormat.getDateTimeInstance().format(Date.from(buildProperties.getTime()));
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().iterator().next().getAuthority();
    }

    public boolean isAdmin() {
        return Role.ADMIN.getName().equals(getRole());
    }

}
