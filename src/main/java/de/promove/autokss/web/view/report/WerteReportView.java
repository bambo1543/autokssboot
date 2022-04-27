package de.promove.autokss.web.view.report;

import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Messung;
import de.promove.autokss.model.Messung_;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.MessageFactory;
import de.promove.autokss.web.util.exporter.MyColoredPdfExporter;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@ViewScope
public class WerteReportView extends AbstractCrudView<Messung> {

    public WerteReportView() {
        super(Messung.class);
    }

    @Override
    protected String getReportHeader() {
        return MessageFactory.getMessage("report.werte");
    }

    @Override
    protected QueryParameter appendQueryParameters(QueryParameter queryParameter) {
        queryParameter.and(Messung_.locked, Boolean.TRUE);
        return queryParameter;
    }

}
