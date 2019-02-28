package br.com.aqueteron.oauth2.authorizer.model;

import br.com.aqueteron.oauth2.authorizer.utils.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class UserApiEntity implements Serializable, Comparable<UserApiEntity> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2154125418981438591L;

	private static final String TO_STRING_FORMAT = "UserApiEntity: { login: %s , password: %s }";

    private final String login;

    private String password;

    public UserApiEntity() {
        this.login = null;
        this.password = null;
    }

    public UserApiEntity(final String login, final String password) {
        this.login = login;
        this.password = password;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof UserApiEntity)) return false;
        UserApiEntity that = (UserApiEntity) o;
        return Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

    @Override
    public int compareTo(final UserApiEntity other) {
        return login.compareTo(other.login);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, this.login, StringUtils.mask(this.password));
    }

}
