package de.promove.autokss.web.converter;

import de.promove.autokss.model.Bereich;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class BereichConverter extends AbstractConverter<Bereich> {

    public BereichConverter() {
        super(Bereich.class);
    }

}
