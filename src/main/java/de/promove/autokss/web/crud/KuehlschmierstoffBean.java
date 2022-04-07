package de.promove.autokss.web.crud;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.model.Kuehlschmierstoff;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class KuehlschmierstoffBean extends AbstractCrudBean<Kuehlschmierstoff> {

    public KuehlschmierstoffBean() {
        super(Kuehlschmierstoff.class);
    }
}
