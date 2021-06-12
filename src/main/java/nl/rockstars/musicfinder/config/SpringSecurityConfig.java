package nl.rockstars.musicfinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value( "${auth.username}" )
    private String username;

    @Value( "${auth.password}" )
    private String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(username).password("{noop}" + password).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/api/artist/**").hasRole("USER")
            .antMatchers("/api/artist").hasRole("USER")
            .antMatchers("/api/song/**").hasRole("USER")
            .antMatchers("/api/song").hasRole("USER")
            .and()
            .csrf().disable()
            .formLogin().disable();
    }
}
