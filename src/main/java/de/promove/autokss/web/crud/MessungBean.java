package de.promove.autokss.web.crud;

import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Messung;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;

@Component
@Scope("view")
public class MessungBean extends AbstractCrudBean<Messung> {

    public MessungBean() {
        super(Messung.class);
    }

}
