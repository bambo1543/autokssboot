package de.promove.autokss.web.view;

import de.promove.autokss.model.Maschine;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class MaschineView extends AbstractCrudView<Maschine> {

    public MaschineView() {
        super(Maschine.class);
    }

}
