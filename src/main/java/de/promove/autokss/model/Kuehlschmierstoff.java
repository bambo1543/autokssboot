package de.promove.autokss.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kuehlschmierstoff extends AbstractNamedBaseEntity implements UploadEntity {

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

    @OneToMany(mappedBy = "kuehlschmierstoff")
    private Set<Maschine> maschinen = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Upload> uploads = new HashSet<>();

    @Transient
    public double getPhSoll() {
        return (phMin + phMax) / 2;
    }
}
