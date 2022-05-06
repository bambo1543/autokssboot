package de.promove.autokss.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Maschine extends AbstractNamedBaseEntity implements UploadEntity {

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

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Upload> uploads = new HashSet<>();

    public Maschine(String name, Bereich bereich) {
        this.name = name;
        this.bereich = bereich;
    }

    public Maschine(Einsatzkonzentration ek, Double tankVolumenLiter, Double wasserstandMaxCm) {
        this.einsatzkonzentration = ek;
        this.wasserstandMaxCm = wasserstandMaxCm;
        this.tankVolumenLiter = tankVolumenLiter;
    }

    @Transient
    public List<Upload> getUploadList() {
        return new ArrayList<>(uploads);
    }

}
