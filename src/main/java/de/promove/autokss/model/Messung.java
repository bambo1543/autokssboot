package de.promove.autokss.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Messung extends AbstractNamedBaseEntity implements LockedEntity {

    @NotNull
    private Date pruefDatum;

    @NotNull
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
}
