package de.promove.autokss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import java.util.Date;

@Entity
public class Messung extends AbstractBaseEntity implements LockedEntity {

    @Column(nullable = false)
    private Date pruefDatum;

    @Column(nullable = false)
    private Date timestamp;

    private double refraktometerKonzProz;
    @Max(value = 14)
    private double ph;
    private double nitrit;

    private double wasserNachgefuellt;
    private double wasserstandCm;
    private double oelNachgefuellt;

    private double refraktometerKonzProz2;
    @Max(value = 14)
    private double ph2;
    private double nitrit2;

    private String bemerkung;
    private boolean locked;

    @ManyToOne(optional = false)
    private User pruefer;

    @ManyToOne(optional = false)
    private Maschine maschine;

    public Messung() {}

    public Messung(Date pruefDatum, User pruefer, Maschine maschine) {
        this.pruefDatum = pruefDatum;
        this.pruefer = pruefer;
        this.maschine = maschine;
    }

    public Date getPruefDatum() {
        return pruefDatum;
    }

    public void setPruefDatum(Date pruefDatum) {
        this.pruefDatum = pruefDatum;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getRefraktometerKonzProz() {
        return refraktometerKonzProz;
    }

    public void setRefraktometerKonzProz(double refraktometerKonzProz) {
        this.refraktometerKonzProz = refraktometerKonzProz;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getNitrit() {
        return nitrit;
    }

    public void setNitrit(double nitrit) {
        this.nitrit = nitrit;
    }

    public double getWasserNachgefuellt() {
        return wasserNachgefuellt;
    }

    public void setWasserNachgefuellt(double wasserNachgefuellt) {
        this.wasserNachgefuellt = wasserNachgefuellt;
    }

    public double getWasserstandCm() {
        return wasserstandCm;
    }

    public void setWasserstandCm(double wasserstandCm) {
        this.wasserstandCm = wasserstandCm;
    }

    public double getOelNachgefuellt() {
        return oelNachgefuellt;
    }

    public void setOelNachgefuellt(double oelNachgefuellt) {
        this.oelNachgefuellt = oelNachgefuellt;
    }

    public double getRefraktometerKonzProz2() {
        return refraktometerKonzProz2;
    }

    public void setRefraktometerKonzProz2(double refraktometerKonzProz2) {
        this.refraktometerKonzProz2 = refraktometerKonzProz2;
    }

    public double getPh2() {
        return ph2;
    }

    public void setPh2(double ph2) {
        this.ph2 = ph2;
    }

    public double getNitrit2() {
        return nitrit2;
    }

    public void setNitrit2(double nitrit2) {
        this.nitrit2 = nitrit2;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public User getPruefer() {
        return pruefer;
    }

    public void setPruefer(User pruefer) {
        this.pruefer = pruefer;
    }

    public Maschine getMaschine() {
        return maschine;
    }

    public void setMaschine(Maschine maschine) {
        this.maschine = maschine;
    }
}
