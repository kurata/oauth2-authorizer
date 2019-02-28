package br.com.aqueteron.oauth2.authorizer.security;

import java.util.Objects;

public class ApiResourceSecurityDefinition extends ApiResourceDefinition {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String scope;

    public ApiResourceSecurityDefinition(final String method, final String path) {
        super(method, path);
        this.scope = "";
    }

    public ApiResourceSecurityDefinition(final String method, final String path, final String scope) {
        super(method, path);
        this.scope = scope;
    }

    public String getScope() {
        return this.scope;
    }

    public String getPathRegex() {
        return getPath().replaceAll("\\{.*\\}", ".*");
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ApiResourceSecurityDefinition)) return false;
        if (!super.equals(obj)) return false;
        ApiResourceSecurityDefinition that = (ApiResourceSecurityDefinition) obj;
        return Objects.equals(getScope(), that.getScope());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getScope());
    }
}
