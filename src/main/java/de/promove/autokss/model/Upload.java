package de.promove.autokss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Upload extends AbstractNamedBaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String contentType;

    @NotNull @Lob
    private Blob content;

}
