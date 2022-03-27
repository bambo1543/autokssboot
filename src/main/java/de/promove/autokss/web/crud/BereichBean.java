package de.promove.autokss.web.crud;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@Component
@ManagedBean
@ViewScoped
public class BereichBean extends AbstractCrudBean<Bereich> {

    public BereichBean() {
        super(Bereich.class);
    }
}
