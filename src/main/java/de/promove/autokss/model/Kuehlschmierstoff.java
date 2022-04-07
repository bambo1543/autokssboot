package de.promove.autokss.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

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

}
