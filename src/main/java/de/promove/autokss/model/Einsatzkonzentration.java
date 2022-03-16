package de.promove.autokss.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Einsatzkonzentration extends AbstractBaseEntity {

    private double min;
    private double max;
    private double soll;

    @OneToMany(mappedBy = "einsatzkonzentration", cascade = CascadeType.ALL)
    private Set<Maschine> maschinen = new HashSet<>();

    public Einsatzkonzentration() {}

    public Einsatzkonzentration(double min, double max, double soll) {
        this.min = min;
        this.max = max;
        this.soll = soll;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getSoll() {
        return soll;
    }

    public void setSoll(double soll) {
        this.soll = soll;
    }

    public Set<Maschine> getMaschinen() {
        return maschinen;
    }

    public void setMaschinen(Set<Maschine> maschinen) {
        this.maschinen = maschinen;
    }
}
