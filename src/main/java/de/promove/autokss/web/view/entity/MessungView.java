package de.promove.autokss.web.view.entity;

import de.promove.autokss.configuration.JsfConfiguration;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Messung;
import de.promove.autokss.model.Messung_;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import org.apache.commons.beanutils.LazyDynaClass;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
