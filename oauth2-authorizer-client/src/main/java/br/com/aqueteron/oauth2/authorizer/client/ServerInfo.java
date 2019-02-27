package br.com.aqueteron.oauth2.authorizer.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "oauth2-authorizer.server")
public class ServerInfo {

    private static final String TO_STRING_FORMAT = "ServerInfo: { scheme: %s , host: %s , port: %s }";

    private String scheme;

    private String host;

    private Integer port;

    public ServerInfo() {
        this.scheme = "http";
        this.host = "localhost";
        this.port = 8080;
    }

    public ServerInfo(String scheme, String host, Integer port) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerInfo)) return false;
        ServerInfo that = (ServerInfo) o;
        return Objects.equals(getScheme(), that.getScheme()) &&
                Objects.equals(getHost(), that.getHost()) &&
                Objects.equals(getPort(), that.getPort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScheme(), getHost(), getPort());
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, this.scheme, this.host, this.port);
    }
}