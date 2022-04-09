package de.promove.autokss.model;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Kuehlschmierstoff extends AbstractBaseEntity implements NamedEntity {

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
}
