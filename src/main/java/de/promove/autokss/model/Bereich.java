package de.promove.autokss.model;

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
public class Bereich extends AbstractNamedBaseEntity {

    @NotNull
    private String name;
    private String description;

    @OneToMany(mappedBy = "bereich", cascade = CascadeType.ALL)
    private Set<Maschine> maschinen = new HashSet<>();

    public Bereich(String name) {
        this.name = name;
    }

    public Bereich(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
