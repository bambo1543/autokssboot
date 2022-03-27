package de.promove.autokss.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;



@Entity
@Getter
@Setter
@NoArgsConstructor
public class Maschine extends AbstractBaseEntity implements NamedEntity {

    @NotNull
    private String name;
//    @NotNull
    private String description;

    @NotNull
    private double tankVolumenLiter;
    @NotNull
    private double wasserstandMaxCm;
    @NotNull
    private double wasserstandMinCm;
    @NotNull
    private double cmEntsprichtLiter;

    private Date letzterEmulsionswechsel;

    @ManyToOne(optional = false)
    private Bereich bereich;

    @ManyToOne
    private Einsatzkonzentration einsatzkonzentration;

    public Maschine(String name, String description, Bereich bereich) {
        this.name = name;
        this.description = description;
        this.bereich = bereich;
    }

}
