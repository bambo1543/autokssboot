package de.promove.autokss.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Messung extends AbstractBaseEntity implements LockedEntity {

    @Column(nullable = false)
    private Date pruefDatum;

    @Column(nullable = false)
    private Date timestamp;

    private double refraktometerKonzProz;
//    @Max(value = 14)
    private double ph;
    private double nitrit;

    private double wasserNachgefuellt;
    private double wasserstandCm;
    private double oelNachgefuellt;

    private double refraktometerKonzProz2;
//    @Max(value = 14)
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

}
