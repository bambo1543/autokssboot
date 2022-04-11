package de.promove.autokss.web.converter;

import de.promove.autokss.model.Kuehlschmierstoff;
import org.springframework.stereotype.Component;

@Component
public class KuehlschmierstoffConverter extends AbstractEntityConverter<Kuehlschmierstoff> {

    public KuehlschmierstoffConverter() {
        super(Kuehlschmierstoff.class);
    }

}
