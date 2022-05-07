package de.promove.autokss.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Messung extends AbstractNamedBaseEntity implements LockedEntity {

    @NotNull
    @Column(nullable=false)
    private Date pruefDatum;

    @NotNull
    @Column(nullable=false)
    private Date timestamp;

    private Double rm1;
    @Max(value = 14)
    private Double ph1;
    private Double nitrit1;

    private Double wasserstandCm;
    private Double wasserNachgefuellt;
    private Double oelNachgefuellt;

    private Double rm2;
    @Max(value = 14)
    private Double ph2;
    private Double nitrit2;

    private String bemerkung;

    @Column(nullable=false)
    private boolean locked;

    @NotNull
    @ManyToOne(optional = false)
    private User pruefer;

    @NotNull
    @ManyToOne(optional = false)
    private Maschine maschine;

    public Messung(Date pruefDatum, User pruefer, Maschine maschine) {
        this.pruefDatum = pruefDatum;
        this.pruefer = pruefer;
        this.maschine = maschine;
    }

    public String getName() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return sdf.format(pruefDatum) + " " + maschine.getName() + " " + pruefer.getEmail();
    }


    public boolean isMessung1Complete() {
        return maschine != null && rm1 != null && ph1 != null && nitrit1 != null && wasserstandCm != null;
    }

    public double getRmSoll() {
        return getMaschine().getEinsatzkonzentration().getSoll();
    }

    public boolean isRm1InRange() {
        return isRmInRange(rm1);
    }

    public boolean isRm2InRange() {
        return isRmInRange(rm2);
    }

    private boolean isRmInRange(double rm) {
        Einsatzkonzentration e = getMaschine().getEinsatzkonzentration();
        return rm > e.getMin() && rm < e.getMax();
    }

    public boolean isPh1InRange() {
        return isPhInRange(ph1);
    }

    public boolean isPh2InRange() {
        return isPhInRange(ph2);
    }

    private boolean isPhInRange(double ph) {
        Kuehlschmierstoff k = getMaschine().getKuehlschmierstoff();
        return ph > k.getPhMin() && ph < k.getPhMax();
    }

    public boolean isNitrit1InRange() {
        return isNitritInRange(nitrit1);
    }

    public boolean isNitrit2InRange() {
        return isNitritInRange(nitrit2);
    }

    private boolean isNitritInRange(double nitrit) {
        Kuehlschmierstoff k = getMaschine().getKuehlschmierstoff();
        return nitrit < k.getNitrit();
    }

}