package de.promove.autokss.web.converter;

import de.promove.autokss.model.Bereich;
import org.springframework.stereotype.Component;

@Component
public class BereichConverter extends AbstractEntityConverter<Bereich> {

    public BereichConverter() {
        super(Bereich.class);
    }

}
