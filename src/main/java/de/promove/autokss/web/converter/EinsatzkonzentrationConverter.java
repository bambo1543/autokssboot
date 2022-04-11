package de.promove.autokss.web.converter;

import de.promove.autokss.model.Einsatzkonzentration;
import org.springframework.stereotype.Component;

@Component
public class EinsatzkonzentrationConverter extends AbstractEntityConverter<Einsatzkonzentration> {

    public EinsatzkonzentrationConverter() {
        super(Einsatzkonzentration.class);
    }

}
