package de.promove.autokss.web.view.entity;

import de.promove.autokss.model.Messung;
import de.promove.autokss.web.SecurityBean;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Calendar;

@Controller
@ViewScope
public class MessungView extends AbstractCrudView<Messung> {

    @Autowired
    private SecurityBean securityBean;

    public MessungView() {
        super(Messung.class);
    }

    @Override
    public boolean isAddable() {
        return securityBean.isAdmin();
    }

    @Override
    public boolean isEditable() {
        return securityBean.isAdmin();
    }

    @Override
    public void save() {
        editItem.setTimestamp(Calendar.getInstance().getTime());
        super.save();
    }

    @Override
    public boolean isDeletable() {
        return securityBean.isAdmin() || super.isDeletable();
    }
}
