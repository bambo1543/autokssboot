package de.promove.autokss.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Maschine extends AbstractNamedBaseEntity implements UploadEntity {

    @NotNull
    @Column(nullable=false)
    private String name;

    @NotNull
    @Column(nullable=false)
    private double tankVolumenLiter;
    @NotNull
    @Column(nullable=false)
    private double wasserstandMaxCm;
    @NotNull
    @Column(nullable=false)
    private double wasserstandMinCm;
    @NotNull
    @Column(nullable=false)
    private double cmEntsprichtLiter;

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

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Upload> uploads = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Sensor> sensoren = new HashSet<>();

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
