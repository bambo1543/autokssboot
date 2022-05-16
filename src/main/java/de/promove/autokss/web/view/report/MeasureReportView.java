package de.promove.autokss.web.view.report;

import de.promove.autokss.dao.generic.QueryParameter;
import de.promove.autokss.model.Messung;
import de.promove.autokss.model.Messung_;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.MessageFactory;
import org.springframework.stereotype.Controller;

@Controller
@ViewScope
public class MeasureReportView extends AbstractCrudView<Messung> {

    public MeasureReportView() {
        super(Messung.class);
    }

    @Override
    protected String getReportHeader() {
        return MessageFactory.getMessage("report.measures");
    }

    @Override
    protected QueryParameter appendQueryParameters(QueryParameter queryParameter) {
        queryParameter.and(Messung_.locked, Boolean.FALSE);
        return queryParameter;
    }

}
