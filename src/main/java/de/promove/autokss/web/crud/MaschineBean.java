package de.promove.autokss.web.crud;

import de.promove.autokss.dao.QueryFetch;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Maschine_;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;

@Component
@Scope("view")
public class MaschineBean extends AbstractCrudBean<Maschine> {

    public MaschineBean() {
        super(Maschine.class);
    }

}
