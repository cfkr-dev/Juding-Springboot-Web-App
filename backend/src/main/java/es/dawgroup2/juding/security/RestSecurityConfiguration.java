package es.dawgroup2.juding.security;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    //Expose AuthenticationManager as a Bean to be used in other services
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**");

        // UserController
        http.authorizeRequests().antMatchers("/api/admin/**").hasRole(Role.A.name());

        // Competition controller
        http.authorizeRequests().antMatchers("/api/competition/{idCompetition}/control").hasAnyRole(Role.R.name());
        http.authorizeRequests().antMatchers("/api/competition/{idCompetition}/join").hasAnyRole(Role.C.name());
        http.authorizeRequests().antMatchers("/api/competition/*").hasAnyRole(Role.C.name(), Role.R.name());

        // LoggedInUserAPIController
        http.authorizeRequests().antMatchers("/api/me/*", "/ranking").hasAnyRole(Role.C.name(), Role.R.name());

        // ImageAPIController
        http.authorizeRequests().antMatchers("/api/image/user/*").hasAnyRole(Role.C.name(), Role.R.name(), Role.A.name());

        // IndexAPIController
        http.authorizeRequests().antMatchers("/api/login", "/api/refresh", "/api/logout").permitAll();
        http.authorizeRequests().antMatchers("/api/signUp/*").permitAll();

        // AdminUserAPIController
        http.authorizeRequests().antMatchers("/api/admin/user/**").hasRole(Role.A.name());

        // Allow all others
		http.authorizeRequests().anyRequest().permitAll();

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf().disable();

        // Disable Http Basic Authentication
        http.httpBasic().disable();

        // Disable Form login Authentication
        http.formLogin().disable();

        // Avoid creating session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add JWT Token filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
