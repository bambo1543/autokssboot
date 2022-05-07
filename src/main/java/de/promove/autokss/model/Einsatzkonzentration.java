package de.promove.autokss.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Einsatzkonzentration extends AbstractNamedBaseEntity {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    @NotNull
    @Column(nullable=false)
    private double min;
    @NotNull
    @Column(nullable=false)
    private double max;
    @NotNull
    @Column(nullable=false)
    private double soll;

    @OneToMany(mappedBy = "einsatzkonzentration", cascade = CascadeType.ALL)
    private Set<Maschine> maschinen = new HashSet<>();

    public Einsatzkonzentration(String name, double min, double max, double soll) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.soll = soll;
    }

}
