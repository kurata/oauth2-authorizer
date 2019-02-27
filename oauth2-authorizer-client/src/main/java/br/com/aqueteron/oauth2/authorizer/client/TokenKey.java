package br.com.aqueteron.oauth2.authorizer.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TokenKey {

    private static final String TO_STRING_FORMAT = "TokenKey: { algorithm: %s , publicKey: %s }";

    @JsonProperty("alg")
    private final String algorithm;

    @JsonProperty("value")
    private final String publicKey;

    public TokenKey() {
        this.algorithm = "";
        this.publicKey = "";
    }

    public TokenKey(String algorithm, String publicKey) {
        this.algorithm = algorithm;
        this.publicKey = publicKey;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenKey)) return false;
        TokenKey tokenKey = (TokenKey) o;
        return Objects.equals(getAlgorithm(), tokenKey.getAlgorithm()) &&
                Objects.equals(getPublicKey(), tokenKey.getPublicKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlgorithm(), getPublicKey());
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, this.algorithm, this.publicKey);
    }
}
