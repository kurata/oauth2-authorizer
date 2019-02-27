package br.com.aqueteron.oauth2.authorizer.client.integration;

//import br.com.aqueteron.oauth2.authorizer.OAuth2AuthorizerApplication;
//import br.com.aqueteron.oauth2.authorizer.client.OAuth2AuthorizerClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
//
//import javax.sql.DataSource;
//
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan(
//        basePackages = {"br.com.aqueteron.oauth2.authorizer"},
//        excludeFilters = {
//                @ComponentScan.Filter(value = OAuth2AuthorizerApplication.class, type = FilterType.ASSIGNABLE_TYPE),
//                @ComponentScan.Filter(value = OAuth2AuthorizerClient.class, type = FilterType.ASSIGNABLE_TYPE)
//        })
//@EnableJpaRepositories("br.com.aqueteron.oauth2.authorizer.business")
//@EntityScan("br.com.aqueteron.oauth2.authorizer.business")
public class IntegrationTestApplication {

//    @Value("${jks.name}")
//    private String jksName;
//
//    @Value("${jks.keypass}")
//    private String jksKeypass;
//
//    @Value("${jks.alias}")
//    private String jksAlias;
//
//    @Value("${jks.aliaspass}")
//    private String jksAliaspass;
//
//    public static void main(final String[] args) {
//        SpringApplication.run(IntegrationTestApplication.class, args);
//    }
//
//    @Bean
//    public TokenStore tokenStore(final DataSource dataSource) {
//        return new JdbcTokenStore(dataSource);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        KeyStoreKeyFactory keyStoreKeyFactory =
//                new KeyStoreKeyFactory(new ClassPathResource(this.jksName), this.jksKeypass.toCharArray());
//        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(this.jksAlias, this.jksAliaspass.toCharArray()));
//        return jwtAccessTokenConverter;
//    }
}
