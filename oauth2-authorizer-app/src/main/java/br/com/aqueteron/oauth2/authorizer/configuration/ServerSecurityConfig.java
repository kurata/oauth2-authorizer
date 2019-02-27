package br.com.aqueteron.oauth2.authorizer.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSecurityConfig.class);

    private static final String USERS_BY_USERNAME_QUERY = "SELECT name , password , enabled FROM user WHERE name = ?";

    private static final String AUTHORITIES_BY_USERNAME_QUERY = "SELECT user_name, role FROM user_role WHERE user_name = ?";

    private static final String LOGIN_PAGE = "/login.html";

    private DataSource dataSource;

    @Autowired
    public ServerSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(this.dataSource)
                .usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**", "/css/**", "/scss/**", "/js/**", "/api/**", "/img/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        LOGGER.debug("Configuring HttpSecurity.");
        http
                .authorizeRequests()
                .antMatchers(LOGIN_PAGE, "/oauth/token").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .exceptionHandling()
                .accessDeniedPage(LOGIN_PAGE.concat("?authorization_error=true"))
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl(LOGIN_PAGE)
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .failureUrl(LOGIN_PAGE.concat("?authentication_error=true"))
                .loginPage(LOGIN_PAGE);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
