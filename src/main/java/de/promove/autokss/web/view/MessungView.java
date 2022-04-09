package de.promove.autokss.web.view;

import de.promove.autokss.model.Messung;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class MessungView extends AbstractCrudView<Messung> {

    public MessungView() {
        super(Messung.class);
    }

}
