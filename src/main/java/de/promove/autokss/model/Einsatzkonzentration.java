package de.promove.autokss.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Einsatzkonzentration extends AbstractBaseEntity implements NamedEntity {

    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private Double min;
    @NotNull
    private Double max;
    @NotNull
    private Double soll;

    @OneToMany(mappedBy = "einsatzkonzentration", cascade = CascadeType.ALL)
    private Set<Maschine> maschinen = new HashSet<>();

    public Einsatzkonzentration(String name, double min, double max, double soll) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.soll = soll;
    }

}
