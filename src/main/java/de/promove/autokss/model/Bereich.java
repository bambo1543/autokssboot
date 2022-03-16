package de.promove.autokss.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Bereich extends AbstractBaseEntity implements NamedEntity {

    private String name;
    private String description;

    @OneToMany(mappedBy = "bereich", cascade = CascadeType.ALL)
    private Set<Maschine> maschinen = new HashSet<>();

    public Bereich() {}

    public Bereich(String name) {
        this.name = name;
    }

    public Bereich(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Maschine> getMaschinen() {
        return maschinen;
    }

    public void setMaschinen(Set<Maschine> maschinen) {
        this.maschinen = maschinen;
    }
}
