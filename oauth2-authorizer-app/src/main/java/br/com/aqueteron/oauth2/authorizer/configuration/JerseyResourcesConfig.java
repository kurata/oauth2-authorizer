package br.com.aqueteron.oauth2.authorizer.configuration;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import br.com.aqueteron.oauth2.authorizer.security.OAuth2AuthorizerAuthenticationFilter;
import br.com.aqueteron.oauth2.authorizer.api.UserApiResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/api")
public class JerseyResourcesConfig extends ResourceConfig {

    @PostConstruct
    public void init() {
        this.configResources();
    }

    private void configResources() {
        register(OAuth2AuthorizerAuthenticationFilter.class);
        register(UserApiResource.class);
    }
}
