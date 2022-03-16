package de.promove.autokss.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bereich extends AbstractBaseEntity implements NamedEntity {

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
