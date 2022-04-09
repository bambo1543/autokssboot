package de.promove.autokss.web.view;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class BereichView extends AbstractCrudView<Bereich> {

    public BereichView() {
        super(Bereich.class);
    }
}
