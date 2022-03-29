package de.promove.autokss.web.converter;

import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.User;
import org.springframework.stereotype.Component;

@Component
public class MaschineConverter extends AbstractConverter<Maschine> {

    public MaschineConverter() {
        super(Maschine.class);
    }

}
