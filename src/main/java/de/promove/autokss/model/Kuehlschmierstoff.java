package de.promove.autokss.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Kuehlschmierstoff extends AbstractBaseEntity implements NamedEntity {

    @NotNull
    private String name;
    private String hersteller;
    @NotNull
    private double phMin;
    @NotNull
    private double phMax;
    @NotNull
    private double nitrat;
    @NotNull
    private double nitrit;
    @NotNull
    private double refraktometer;



}
