package de.promove.autokss.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public enum Role implements GrantedAuthority {

    ADMIN, USER;

    public String getName() {
        return name();
    }
    @Override
    public String getAuthority() {
        return name();
    }

    public static List<Role> getRoles(Collection<? extends GrantedAuthority> roles) {
        if(roles.contains(ADMIN)) {
            return List.of(Role.values());
        } else if(roles.contains(USER)) {
            return List.of(USER);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
