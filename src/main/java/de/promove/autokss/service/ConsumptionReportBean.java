package de.promove.autokss.service;

import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.NamedEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
public class ConsumptionReportBean implements NamedEntity {

    private String id = UUID.randomUUID().toString();
    private Maschine maschine;
    private Date from;
    private Date to;
    private double oilOpeningBalance;
    private double oilFilled;
    private double oilEndBalance;
    
    private double waterOpeningBalance;
    private double waterFilled;
    private double waterEndBalance;

    public ConsumptionReportBean(Maschine maschine) {
        this.maschine = maschine;
    }
    
    public double getOilConsumption() {
        return oilOpeningBalance + oilFilled - oilEndBalance;
    }

    public double getWaterConsumption() {
        return waterOpeningBalance + waterFilled - waterEndBalance;
    }

    @Override
    public String getName() {
        return maschine.getName();
    }

    public int getDays() {
        return Days.daysBetween(new DateTime(from.getTime()), new DateTime(to.getTime())).getDays();
    }

    public double getOilConsumptionPerDay() {
        return getOilConsumption() / getDays();
    }
    public double getWaterConsumptionPerDay() {
        return getWaterConsumption() / getDays();
    }
}
