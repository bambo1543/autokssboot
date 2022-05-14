package de.promove.autokss.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersistentProperties extends AbstractBaseEntity {

    @NotNull
    @Column(name="key_column", nullable = false, unique = true)
    private String key;

    @Column(name="value_column")
    private String value;

}
