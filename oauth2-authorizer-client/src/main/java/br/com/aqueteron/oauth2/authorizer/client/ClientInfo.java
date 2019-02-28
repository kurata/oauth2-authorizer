package br.com.aqueteron.oauth2.authorizer.client;

import br.com.aqueteron.oauth2.authorizer.utils.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "oauth2-authorizer.client")
public class ClientInfo {

    private static final String TO_STRING_FORMAT = "ClientInfo: { id: %s , secret: %s , scope: %s }";

    private String id;

    private String secret;

    private String scope;

    public ClientInfo() {
        this.id = "";
        this.secret = "";
        this.scope = "";
    }

    public ClientInfo(final String id, final String secret, final String scope) {
        this.id = id;
        this.secret = secret;
        this.scope = scope;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientInfo)) return false;
        ClientInfo that = (ClientInfo) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getSecret(), that.getSecret()) &&
                Objects.equals(getScope(), that.getScope());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSecret(), getScope());
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, this.id, StringUtils.mask(this.secret), this.scope);
    }
}
