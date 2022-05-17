package de.promove.autokss.web.view;

import de.promove.autokss.dao.generic.QueryOrder;
import de.promove.autokss.dao.generic.QueryParameter;
import de.promove.autokss.dao.generic.QueryParameterEntry;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Messung;
import de.promove.autokss.model.Messung_;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.web.util.View;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@View
public class ChartView {

    @Autowired
    @Qualifier("genericService")
    private GenericService service;

    @Autowired
    private SelectItemsBean selectItemsBean;

    @Getter
    private Maschine maschine;

    @Getter
    private LineChartModel lineModel;

    @PostConstruct
    public void init() {
        maschine = selectItemsBean.getMaschine().get(0);
        createLineModel();
    }

    private void createLineModel() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        List<Messung> messungen = service.list(Messung.class, QueryParameter.with(Messung_.pruefDatum, QueryParameterEntry.Operator.GE, calendar.getTime())
                .and(Messung_.locked, Boolean.TRUE)
                .and(Messung_.maschine, maschine),
                QueryOrder.by(Messung_.pruefDatum));

        lineModel = new LineChartModel();

        ChartData data = new ChartData();
        data.addChartDataSet(createChartDataSet(messungen, Messung::getPh2, "Ph", "rgb(75, 192, 192)"));
        data.addChartDataSet(createChartDataSet(messungen, Messung::getNitrit2, "Nitrit", "rgb(47, 116, 75)"));
        data.addChartDataSet(createChartDataSet(messungen, Messung::getRm2, "Öl-Konzentration", "rgb(188, 37, 108)"));

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        List<String> labels = messungen.stream().map(date -> dateFormat.format(date.getPruefDatum()))
                .collect(Collectors.toList());
        data.setLabels(labels);

//        LineChartOptions options = new LineChartOptions();
//
//        CartesianScales cScales = new CartesianScales();
//        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
//        linearAxes.setId("date-x-axis");
//        linearAxes.setPosition("bottom");
//        cScales.addXAxesData(linearAxes);
//        options.setScales(cScales);
//        lineModel.setOptions(options);

        lineModel.setData(data);
    }

    @NotNull
    private LineChartDataSet createChartDataSet(List<Messung> messungen, Function<Messung, Double> function, String label, String borderColor) {
        LineChartDataSet dataSetPh = new LineChartDataSet();
        List<Object> collect = messungen.stream().map(function).collect(Collectors.toList());
        dataSetPh.setData(collect);
        dataSetPh.setLabel(label);
        dataSetPh.setXaxisID("date-x-axis");
        dataSetPh.setBorderColor(borderColor);
        dataSetPh.setTension(0.1);
        return dataSetPh;
    }

    public void setMaschine(Maschine maschine) {
        this.maschine = maschine;
        createLineModel();
    }
}
