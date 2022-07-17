package de.promove.autokss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sensor extends AbstractNamedBaseEntity {

    private String mac;

    private String ip;

    @ManyToOne
    private Maschine maschine;

    @Transient
    public String getName() {
        return mac;
    }

}
