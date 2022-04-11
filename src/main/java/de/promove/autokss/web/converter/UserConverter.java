package de.promove.autokss.web.converter;

import de.promove.autokss.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractEntityConverter<User> {

    public UserConverter() {
        super(User.class);
    }

}
