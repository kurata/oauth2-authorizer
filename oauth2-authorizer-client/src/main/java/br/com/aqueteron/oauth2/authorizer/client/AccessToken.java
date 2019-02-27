package br.com.aqueteron.oauth2.authorizer.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AccessToken {

    private static final String TO_STRING_FORMAT = "AccessToken: { jti: %s , value: %s , type: %s , expiresIn: %s , scope: %s }";

    private final String jti;

    @JsonProperty("access_token")
    private final String value;

    @JsonProperty("token_type")
    private String type;

    @JsonProperty("expires_in")
    private Long expiresIn;

    private String scope;

    public AccessToken() {
        this.value = "";
        this.jti = "";
    }

    public AccessToken(final String jti, final String value) {
        this.value = value;
        this.jti = jti;
    }

    public String getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return this.jti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessToken)) return false;
        AccessToken that = (AccessToken) o;
        return Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getExpiresIn(), that.getExpiresIn()) &&
                Objects.equals(getScope(), that.getScope()) &&
                Objects.equals(getJti(), that.getJti());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getJti());
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, this.jti, this.value, this.type, this.expiresIn, this.scope);
    }
}
