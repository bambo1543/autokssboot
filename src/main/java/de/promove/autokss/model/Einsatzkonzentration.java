package de.promove.autokss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
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
