package br.com.aqueteron.oauth2.authorizer.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class JwtTokenClaims implements Serializable {

    private static final String TO_STRING_FORMAT = "JwtTokenClaims: { jwtId: %s , audience: %s , userName: %s , scope: %s , active: %s , expirationTime: %s , authorities: %s , clientId: %s }";

    @JsonProperty("jti")
    private final String jwtId;

    @JsonProperty("aud")
    private Set<String> audience;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("scope")
    private Set<String> scope;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("exp")
    private Long expirationTime;

    @JsonProperty("authorities")
    private Set<String> authorities;

    @JsonProperty("client_id")
    private String clientId;

    public JwtTokenClaims() {
        this.jwtId = "";
    }

    public JwtTokenClaims(final String jwtId){
        this.jwtId = jwtId;
    }

    public String getJwtId() {
        return this.jwtId;
    }

    public Set<String> getAudience() {
        return this.audience;
    }

    public void setAudience(Set<String> audience) {
        this.audience = audience;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getScope() {
        return this.scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Set<String> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JwtTokenClaims)) return false;
        JwtTokenClaims that = (JwtTokenClaims) o;
        return Objects.equals(getJwtId(), that.getJwtId()) &&
                Objects.equals(getAudience(), that.getAudience()) &&
                Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getScope(), that.getScope()) &&
                Objects.equals(getActive(), that.getActive()) &&
                Objects.equals(getExpirationTime(), that.getExpirationTime()) &&
                Objects.equals(getAuthorities(), that.getAuthorities()) &&
                Objects.equals(getClientId(), that.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.jwtId);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, this.jwtId, this.audience, this.userName, this.scope, this.active, this.expirationTime, this.authorities, this.clientId);
    }
}
