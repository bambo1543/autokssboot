package de.promove.autokss.model;

import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kuehlschmierstoff extends AbstractNamedBaseEntity {

    @NotNull
    private String name;
    private String hersteller;
    @NotNull
    private Double phMin;
    @NotNull
    private Double phMax;
    @NotNull
    private Double refraktometer;
    @NotNull
    private Double nitrat;
    @NotNull
    private Double nitrit;
    @NotNull
    private Double wasserhaerte;

    private String datanblattName;

    @Lob
    private Blob datenblatt;

    @OneToMany(mappedBy = "kuehlschmierstoff")
    private Set<Maschine> maschinen = new HashSet<>();
}
