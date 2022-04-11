package de.promove.autokss.web.converter;

import de.promove.autokss.model.Maschine;
import org.springframework.stereotype.Component;

@Component
public class MaschineConverter extends AbstractEntityConverter<Maschine> {

    public MaschineConverter() {
        super(Maschine.class);
    }

}
