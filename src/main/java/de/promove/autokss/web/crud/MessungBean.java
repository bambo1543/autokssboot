package de.promove.autokss.web.crud;

import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Messung;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@Component
@ManagedBean
@ViewScoped
public class MessungBean extends AbstractCrudBean<Messung> {

    public MessungBean() {
        super(Messung.class);
    }

}
