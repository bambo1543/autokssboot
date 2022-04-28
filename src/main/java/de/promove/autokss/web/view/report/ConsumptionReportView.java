package de.promove.autokss.web.view.report;

import de.promove.autokss.model.IdEntity;
import de.promove.autokss.service.ConsumptionReportBean;
import de.promove.autokss.service.ConsumptionReportService;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.common.crud.LazyIdEntityDataModel;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.ColumnUtils;
import de.promove.autokss.web.util.MessageFactory;
import de.promove.autokss.web.util.exporter.MyColoredPdfExporter;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@ViewScope
public class ConsumptionReportView implements FilteredView {

    @Autowired
    private ConsumptionReportService service;

    @Getter
    private PDFOptions pdfOptions;

    @Getter
    private MyColoredPdfExporter pdfExporter;
    @Getter
    private LazyIdEntityDataModel<ConsumptionReportBean> dataModel;
    @Getter @Setter
    private ConsumptionReportBean selectedItem;
    private Map<String, FilterMeta> filterMap;

    @PostConstruct
    public void init() {
        dataModel = new LazyIdEntityDataModel<>() {

            List<ConsumptionReportBean> consumptionReportBeans;
            @Override
            public int count(Map<String, FilterMeta> map) {
                ConsumptionReportView.this.filterMap = map;
                consumptionReportBeans = service.getConsumptionReportBeans(
                        map.containsKey("from") ? (LocalDate) map.get("from").getFilterValue() : null,
                        map.containsKey("to") ? (LocalDate) map.get("to").getFilterValue() : null);
                this.setRowCount(consumptionReportBeans.size());
                return consumptionReportBeans.size();
            }

            @Override
            public List<ConsumptionReportBean> load(int first, int pageSize, Map<String, SortMeta> sortMap, Map<String, FilterMeta> filterMap) {
                return consumptionReportBeans;
            }
        };

        pdfExporter = new MyColoredPdfExporter(MessageFactory.getMessage("report.consumption"), this);

        pdfOptions = new PDFOptions();
        pdfOptions.setCellFontSize("12");
        pdfOptions.setFontName("Courier");
        pdfOptions.setOrientation(PDFOrientationType.LANDSCAPE);
    }

    @Override
    public Class<?> getClazz() {
        return ConsumptionReportBean.class;
    }

    @Override
    public String getI18nFieldName(String entityField) {
        return entityField;
    }

    @Override
    public Map<String, FilterMeta> getFilterMap() {
        return filterMap;
    }

    public String getFilename() {
        return (MessageFactory.getMessage("report.consumption"))
                + " " + DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
    }

    public String formatColumn(UIColumn column) {
        return ColumnUtils.formatColumn(column);
    }

}
