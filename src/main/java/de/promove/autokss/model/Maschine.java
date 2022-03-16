package de.promove.autokss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
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

    public Maschine(String name, Bereich bereich) {
        this.name = name;
        this.bereich = bereich;
    }

}
