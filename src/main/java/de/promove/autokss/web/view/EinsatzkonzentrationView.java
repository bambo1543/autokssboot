package de.promove.autokss.web.view;

import de.promove.autokss.configuration.JsfConfiguration;
import de.promove.autokss.model.Bereich;
import de.promove.autokss.model.Einsatzkonzentration;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@ViewScope
public class EinsatzkonzentrationView extends AbstractCrudView<Einsatzkonzentration> {

    public EinsatzkonzentrationView() {
        super(Einsatzkonzentration.class);
    }
}
