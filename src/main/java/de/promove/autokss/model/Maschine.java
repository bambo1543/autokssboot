package de.promove.autokss.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Maschine extends AbstractBaseEntity implements NamedEntity {

    private String name;
    private String produktBezeichnung;
    private String description;

    private double tankVolumenLiter;
    private double wasserstandMaxCm;
    private double wasserstandMinCm;
    private double cmEntsprichtLiter;

    private Date letzterEmulsionswechsel;

    @ManyToOne(optional = false)
    private Bereich bereich;

    @ManyToOne(optional = false)
    private Einsatzkonzentration einsatzkonzentration;

    public Maschine() {}

    public Maschine(String name, Bereich bereich) {
        this.name = name;
        this.bereich = bereich;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduktBezeichnung() {
        return produktBezeichnung;
    }

    public void setProduktBezeichnung(String produktBezeichnung) {
        this.produktBezeichnung = produktBezeichnung;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTankVolumenLiter() {
        return tankVolumenLiter;
    }

    public void setTankVolumenLiter(double tankVolumenLiter) {
        this.tankVolumenLiter = tankVolumenLiter;
    }

    public double getWasserstandMaxCm() {
        return wasserstandMaxCm;
    }

    public void setWasserstandMaxCm(double wasserstandMaxCm) {
        this.wasserstandMaxCm = wasserstandMaxCm;
    }

    public double getWasserstandMinCm() {
        return wasserstandMinCm;
    }

    public void setWasserstandMinCm(double wasserstandMinCm) {
        this.wasserstandMinCm = wasserstandMinCm;
    }

    public double getCmEntsprichtLiter() {
        return cmEntsprichtLiter;
    }

    public void setCmEntsprichtLiter(double cmEntsprichtLiter) {
        this.cmEntsprichtLiter = cmEntsprichtLiter;
    }

    public Date getLetzterEmulsionswechsel() {
        return letzterEmulsionswechsel;
    }

    public void setLetzterEmulsionswechsel(Date letzterEmulsionswechsel) {
        this.letzterEmulsionswechsel = letzterEmulsionswechsel;
    }

    public Bereich getBereich() {
        return bereich;
    }

    public void setBereich(Bereich bereich) {
        this.bereich = bereich;
    }

    public Einsatzkonzentration getEinsatzkonzentration() {
        return einsatzkonzentration;
    }

    public void setEinsatzkonzentration(Einsatzkonzentration einsatzkonzentration) {
        this.einsatzkonzentration = einsatzkonzentration;
    }
}
