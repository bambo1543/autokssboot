package de.promove.autokss.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(nullable=false)
    private String name;
    private String hersteller;
    @NotNull
    @Column(nullable=false)
    private double phMin;
    @NotNull
    @Column(nullable=false)
    private double phMax;
    @NotNull
    @Column(nullable=false)
    private double refraktometer;
    @NotNull
    @Column(nullable=false)
    private double nitrat;
    @NotNull
    @Column(nullable=false)
    private double nitrit;
    @NotNull
    @Column(nullable=false)
    private double wasserhaerte;

    private String datanblattName;

    @Lob
    private Blob datenblatt;

    @OneToMany(mappedBy = "kuehlschmierstoff")
    private Set<Maschine> maschinen = new HashSet<>();

    @Transient
    public double getPhSoll() {
        return (phMin + phMax) / 2;
    }
}
