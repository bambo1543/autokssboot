package de.promove.autokss.web.view.entity;

import de.promove.autokss.dao.generic.QueryFetch;
import de.promove.autokss.model.Kuehlschmierstoff;
import de.promove.autokss.model.Kuehlschmierstoff_;
import de.promove.autokss.web.common.crud.AbstractUploadCrudView;
import de.promove.autokss.web.scope.ViewScope;
import org.springframework.stereotype.Controller;

@Controller
@ViewScope
public class KuehlschmierstoffView extends AbstractUploadCrudView<Kuehlschmierstoff> {

    public KuehlschmierstoffView() {
        super(Kuehlschmierstoff.class, new QueryFetch[]{QueryFetch.withLeftJoin(Kuehlschmierstoff_.uploads)});
    }

}
