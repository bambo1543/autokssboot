package de.promove.autokss.web.crud;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;

@Component
@Scope("view")
public class BereichBean extends AbstractCrudBean<Bereich> {

    public BereichBean() {
        super(Bereich.class);
    }
}
