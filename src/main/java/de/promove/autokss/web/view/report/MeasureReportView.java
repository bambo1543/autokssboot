package de.promove.autokss.web.view.report;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.Messung;
import de.promove.autokss.model.Messung_;
import de.promove.autokss.model.NamedEntity;
import de.promove.autokss.web.common.MessageBundleLoader;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.MessageFactory;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.FacesEvent;
import lombok.Getter;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@ViewScope
public class MeasureReportView extends AbstractCrudView<Messung> {

    public MeasureReportView() {
        super(Messung.class);
    }

    @Override
    protected QueryParameter appendQueryParameters(QueryParameter queryParameter) {
        queryParameter.and(Messung_.locked, Boolean.FALSE);
        return queryParameter;
    }

    public String getFilename() {
        return MessageFactory.getMessage("report.measures") + " " + DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
    }
}
