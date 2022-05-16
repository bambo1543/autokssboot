package de.promove.autokss.service;

import de.promove.autokss.dao.generic.QueryOrder;
import de.promove.autokss.dao.generic.QueryParameter;
import de.promove.autokss.dao.generic.QueryParameterEntry;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Maschine_;
import de.promove.autokss.model.Messung;
import de.promove.autokss.model.Messung_;
import de.promove.autokss.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ConsumptionReportService {

    @Autowired
    private GenericService genericService;

    public List<ConsumptionReportBean> getConsumptionReportBeans(LocalDate from, LocalDate to) {
        List<ConsumptionReportBean> result = new ArrayList<>();
        List<Maschine> maschines = genericService.listAll(Maschine.class, QueryOrder.by(Maschine_.name));
        for (Maschine maschine : maschines) {
            ConsumptionReportBean bean = new ConsumptionReportBean(maschine);

            List<Messung> messungen = genericService.list(Messung.class, QueryParameter.with(Messung_.maschine, maschine).and(Messung_.locked, Boolean.TRUE)
                            .and(Messung_.pruefDatum, QueryParameterEntry.Operator.GE, from == null ? new Date(0) : DateUtils.localDateToDateConversion(from))
                            .and(Messung_.pruefDatum, QueryParameterEntry.Operator.LE, to == null ? new Date() : DateUtils.localDateToDateConversion(to)),
                    QueryOrder.by(Messung_.pruefDatum));

            if(!messungen.isEmpty()) {
                Double cmEntsprichtLiter = maschine.getCmEntsprichtLiter() == 0D ? maschine.getTankVolumenLiter() / maschine.getWasserstandMaxCm() : maschine.getCmEntsprichtLiter();

                Messung ersteMessung = messungen.get(0);
                bean.setFrom(ersteMessung.getPruefDatum());

                double tankinhaltInLiter = ersteMessung.getWasserstandCm() * cmEntsprichtLiter;
                bean.setOilOpeningBalance(tankinhaltInLiter * ersteMessung.getRm1() / 100D);
                bean.setWaterOpeningBalance(tankinhaltInLiter - bean.getOilOpeningBalance());

                double oilFilled = 0D;
                double waterFilled = 0D;
                for(int i = 1; i < messungen.size(); i++) {
                    oilFilled += messungen.get(i).getOelNachgefuellt();
                    waterFilled += messungen.get(i).getWasserNachgefuellt();
                }
                bean.setOilFilled(oilFilled);
                bean.setWaterFilled(waterFilled);

                Messung letzteMessung = messungen.get(messungen.size() - 1);
                bean.setTo(letzteMessung.getPruefDatum());

                double tankinhaltInLiterEnde = letzteMessung.getWasserstandCm() * cmEntsprichtLiter;
                bean.setOilEndBalance(tankinhaltInLiterEnde * letzteMessung.getRm2() / 100D);
                bean.setWaterEndBalance(tankinhaltInLiterEnde - bean.getOilEndBalance());

                result.add(bean);
            }
        }
        return result;
    }

}
