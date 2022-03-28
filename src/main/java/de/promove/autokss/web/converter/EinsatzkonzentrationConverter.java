package de.promove.autokss.web.converter;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.model.Einsatzkonzentration;
import org.springframework.stereotype.Component;

@Component
public class EinsatzkonzentrationConverter extends AbstractConverter<Einsatzkonzentration> {

    public EinsatzkonzentrationConverter() {
        super(Einsatzkonzentration.class);
    }

}
