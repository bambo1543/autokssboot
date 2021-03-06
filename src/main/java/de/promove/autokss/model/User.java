package de.promove.autokss.model;

import de.promove.autokss.util.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractNamedBaseEntity implements UserDetails {

    private static final long serialVersionUID = 12345327L;

    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull
    @Column(nullable=false)
    private String password;

    private String firstName;
    private String lastName;
    private String comment;

    @NotNull
    @Column(nullable=false)
    private boolean enabled;
    @NotNull
    @Column(nullable=false)
    private boolean locked;
    @NotNull
    @Column(nullable = false)
    private Date accountExpire;
    private Date credentialsExpire;

    @NotNull
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String theme;

    public User(String email, String password, String firstName, String lastName, String comment, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.comment = comment;
        this.enabled = true;
        this.locked = false;
        this.accountExpire = DateUtils.future();
        this.credentialsExpire = DateUtils.future();
        this.role = role;
    }

    @Override
    public String getName() {
        return StringUtils.hasText(getFirstName()) ? getFirstName() + " " + getLastName() : getLastName();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpire.after(new Date());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
//        return credentialsExpire.after(new Date());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

}