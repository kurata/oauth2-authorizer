package br.com.aqueteron.oauth2.authorizer.business;

import br.com.aqueteron.common.utility.StringUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User implements Serializable, Comparable<User> {

    private static final String TO_STRING_FORMAT = "User: { login: %s , password: %s , isEnabled: %s , roles: %s }";

    @Id
    @Column(name = "name")
    private final String login;

    private String password;

    @Column(name = "enabled", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isEnabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_name")})
    @Column(name = "role")
    private Set<String> roles;

    public User() {
        this.login = null;
        this.roles = new HashSet<>();
    }

    public User(final String login) {
        this.login = login;
        this.roles = new HashSet<>();
    }

    public User(final User original) {
        this.login = original.getLogin();
        this.password = original.getPassword();
        this.isEnabled = original.getEnabled();
        this.roles = new HashSet<>(original.getRoles());
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(final Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    public void addRole(final String role) {
        this.roles.add(role);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(isEnabled, user.isEnabled) &&
                Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

    @Override
    public int compareTo(final User other) {
        return login.compareTo(other.login);
    }

    @Override
    public String toString() {
        return String.format(
                TO_STRING_FORMAT,
                this.login,
                StringUtils.mask(this.password),
                this.isEnabled,
                this.roles);
    }
}
