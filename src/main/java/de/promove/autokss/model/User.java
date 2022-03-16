package de.promove.autokss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractBaseEntity implements NamedEntity {

    @NotNull(message = "{validation.NotNull.message}")
    @Length
    @Column
    private String firstName;
    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;
    private String comment;

    public User() {
    }

    public User(String firstName, String lastName, String email, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return getFirstName();
    }

    @Override
    public String getDescription() {
        return getComment();
    }

}