package es.dawgroup2.juding.security;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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

        // All users
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/login", "/api/refresh", "/api/logout").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/referees","/api/competitors").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/passwordRecovery/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/passwordRecovery/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/index-email").permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/posts").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/posts/recent").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/posts/{id}").permitAll();

        //Admin & Referees & Competitors
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/{id}/image", "/api/competitors/{id}/image", "/api/referees/{id}/image").hasAnyRole(Role.C.name(), Role.R.name(), Role.A.name());

        //Referees & Competitors
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitions/{id}").hasAnyRole(Role.C.name(), Role.R.name());

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitions/{id}/past").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitions/{id}/current").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitions/{id}/future").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/ranking").hasAnyRole(Role.C.name(), Role.R.name());

        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/users/{id}/image", "/api/competitors/{id}/image").hasAnyRole(Role.C.name());
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/users/{id}/image", "/api/referees/{id}/image").hasAnyRole(Role.R.name());

        //Admin
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/competitions").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitions").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/competitions/{id}").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/competitions/{id}").hasRole(Role.A.name());

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/posts").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/posts/{id}").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/posts/{id}/image").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/posts/{id}").hasRole(Role.A.name());

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitors").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/referees").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/referees/applications").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/referees/applications/{id}").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/competitors/{id}", "/api/referees/{id}").hasRole(Role.A.name());

        //Referees
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/referees/{id}").hasAnyRole(Role.R.name());
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/referees/{id}").hasAnyRole(Role.R.name());

        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/competitions/{id}/control").hasAnyRole(Role.R.name());

        //Competitors
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/competitions/members/{id}").hasAnyRole(Role.C.name());

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitors/{id}").hasAnyRole(Role.C.name(), Role.A.name());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/competitors/points/{id}").hasAnyRole(Role.C.name());
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/competitors/{id}").hasAnyRole(Role.C.name(), Role.A.name());
        
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
