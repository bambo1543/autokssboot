package de.promove.autokss.model;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Messung extends AbstractBaseEntity implements LockedEntity, NamedEntity {

    @Column(nullable = false)
    private Date pruefDatum;

    @Column(nullable = false)
    private Date timestamp;

    private double refraktometerKonzProz;
    @Max(value = 14)
    private double ph;
    private double nitrit;

    private double wasserstandCm;
    private double wasserNachgefuellt;
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

    public Messung(Date pruefDatum, User pruefer, Maschine maschine) {
        this.pruefDatum = pruefDatum;
        this.pruefer = pruefer;
        this.maschine = maschine;
    }

    public String getName() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return sdf.format(pruefDatum) + " " + maschine.getName() + " " + pruefer.getEmail();
    }

}
