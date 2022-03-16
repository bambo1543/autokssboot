package de.promove.autokss.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractBaseEntity implements NamedEntity {

    @NotNull(message = "{validation.NotNull.message}")
    private String firstName;
    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;
    private String comment;

    @Override
    public String getName() {
        return getFirstName();
    }

    @Override
    public String getDescription() {
        return getComment();
    }

}