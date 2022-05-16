package de.promove.autokss.web.view.entity;

import de.promove.autokss.model.Messung;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import org.springframework.stereotype.Controller;

@Controller
@ViewScope
public class MessungView extends AbstractCrudView<Messung> {

    public MessungView() {
        super(Messung.class);
    }

    @Override
    public boolean isAddable() {
        return false;
    }

    @Override
    public boolean isEditable() {
        return super.isEditable();
    }
}
