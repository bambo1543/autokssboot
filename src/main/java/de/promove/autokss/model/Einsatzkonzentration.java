package de.promove.autokss.model;

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
public class Einsatzkonzentration extends AbstractBaseEntity {

    private double min;
    private double max;
    private double soll;

    @OneToMany(mappedBy = "einsatzkonzentration", cascade = CascadeType.ALL)
    private Set<Maschine> maschinen = new HashSet<>();

    public Einsatzkonzentration(double min, double max, double soll) {
        this.min = min;
        this.max = max;
        this.soll = soll;
    }

}
