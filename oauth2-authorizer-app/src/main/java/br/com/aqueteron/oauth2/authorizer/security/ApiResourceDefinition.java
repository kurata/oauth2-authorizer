package br.com.aqueteron.oauth2.authorizer.security;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents an API Resource meta information. With resource method and path.
 */
public class ApiResourceDefinition implements Serializable {

    private final String method;

    private final String path;

    public ApiResourceDefinition(final String method, final String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiResourceDefinition)) return false;
        ApiResourceDefinition that = (ApiResourceDefinition) o;
        return Objects.equals(getMethod(), that.getMethod()) &&
                Objects.equals(getPath(), that.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getPath());
    }
}
