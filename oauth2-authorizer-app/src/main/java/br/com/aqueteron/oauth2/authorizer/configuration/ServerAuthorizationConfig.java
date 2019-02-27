package br.com.aqueteron.oauth2.authorizer.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class ServerAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final TokenStore tokenStore;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public ServerAuthorizationConfig(final DataSource dataSource, final JwtAccessTokenConverter jwtAccessTokenConverter, final TokenStore tokenStore, final AuthenticationManager authenticationManager) {
        this.dataSource = dataSource;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.tokenStore = tokenStore;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.dataSource);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .accessTokenConverter(this.jwtAccessTokenConverter)
                .tokenStore(this.tokenStore)
                .tokenEnhancer(this.jwtAccessTokenConverter)
                .authenticationManager(this.authenticationManager);
    }

}
