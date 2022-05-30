package de.promove.autokss.web.view;

import de.promove.autokss.dao.generic.QueryOrder;
import de.promove.autokss.dao.generic.QueryParameter;
import de.promove.autokss.dao.generic.QueryParameterEntry;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Messung;
import de.promove.autokss.model.Messung_;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.web.util.GrowlMessenger;
import de.promove.autokss.web.util.View;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.chartistjsf.model.chart.*;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;
import java.util.function.Function;

@View
public class ChartView {

    @Autowired
    @Qualifier("genericService")
    private GenericService service;

    @Autowired
    private SelectItemsBean selectItemsBean;

    @Getter @Setter
    private Maschine maschine;

    @Getter @Setter
    private boolean animation = true;

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
        lineModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);
        lineModel.setAnimateAdvanced(animation);

        lineModel.addSeries(createChartDataSet(messungen, Messung::getPh2, "Ph"));
        lineModel.addSeries(createChartDataSet(messungen, Messung::getNitrit2, "Nitrit"));
        lineModel.addSeries(createChartDataSet(messungen, Messung::getRm2, "Öl-Konzentration"));
        lineModel.updateYAxisRangeByChartSeries();

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setType(Axis.Type.FIXED_SCALE_AXIS);
        xAxis.setDivisor(10);
        xAxis.setDateFormat("DD.MM.YY");

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setType(Axis.Type.FIXED_SCALE_AXIS);
        yAxis.setDivisor(10);
        yAxis.setRoundDigits(1);
    }

    private DateChartSeries createChartDataSet(List<Messung> messungen, Function<Messung, Double> function, String label) {
        DateChartSeries series = new DateChartSeries();
        Map<Date, Number> map =new HashMap<>();
        for (Messung messung : messungen) {
            map.put(messung.getPruefDatum(), function.apply(messung));
        }
        series.setData(map);
        series.setName(label);
        return series;
    }

    public void itemSelect(ItemSelectEvent event) {
        Map<Timestamp, Double> data = (Map) lineModel.getSeries().get(event.getSeriesIndex()).getData();
        ArrayList<Timestamp> keys = new ArrayList<>(data.keySet());
        Collections.sort(keys);
        Timestamp key = keys.get(event.getItemIndex());

        GrowlMessenger.publish(lineModel.getSeries().get(event.getSeriesIndex()).getName(),
                DateFormat.getDateInstance().format(key) + ": " + data.get(key));
    }

    public void maschineChanged(SelectEvent event) {
        createLineModel();
    }
    public void animationChanged() {
        createLineModel();
    }
}
