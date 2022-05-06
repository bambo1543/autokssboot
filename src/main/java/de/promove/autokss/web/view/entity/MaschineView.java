package de.promove.autokss.web.view.entity;

import de.promove.autokss.dao.QueryFetch;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Maschine_;
import de.promove.autokss.web.common.crud.AbstractUploadCrudView;
import de.promove.autokss.web.scope.ViewScope;
import org.springframework.stereotype.Controller;

@Controller
@ViewScope
public class MaschineView extends AbstractUploadCrudView<Maschine> {

    public MaschineView() {
        super(Maschine.class, new QueryFetch[]{QueryFetch.withLeftJoin(Maschine_.uploads)});
    }

}