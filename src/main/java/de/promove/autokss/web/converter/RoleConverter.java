package de.promove.autokss.web.converter;

import de.promove.autokss.model.Role;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<Role> {
    @Override
    public Role getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return Role.valueOf(value);
        } catch(IllegalArgumentException ignore) {}
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Role value) {
        if(value == null) return null;

        return value.name();
    }
}
