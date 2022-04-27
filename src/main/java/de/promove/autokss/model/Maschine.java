package de.promove.autokss.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.Date;



@Entity
@Getter
@Setter
@NoArgsConstructor
public class Maschine extends AbstractNamedBaseEntity {

    @NotNull
    private String name;

    @NotNull
    private Double tankVolumenLiter;
    @NotNull
    private Double wasserstandMaxCm;
    @NotNull
    private Double wasserstandMinCm;
    @NotNull
    private Double cmEntsprichtLiter;

    private Date letzterEmulsionswechsel;

    @NotNull
    @ManyToOne(optional = false)
    private Bereich bereich;

    @NotNull
    @ManyToOne(optional = false)
    private Einsatzkonzentration einsatzkonzentration;

    @NotNull
    @ManyToOne(optional = false)
    private Kuehlschmierstoff kuehlschmierstoff;

    public Maschine(String name, Bereich bereich) {
        this.name = name;
        this.bereich = bereich;
    }

    public Maschine(Einsatzkonzentration ek, Double tankVolumenLiter, Double wasserstandMaxCm) {
        this.einsatzkonzentration = ek;
        this.wasserstandMaxCm = wasserstandMaxCm;
        this.tankVolumenLiter = tankVolumenLiter;
    }

}
