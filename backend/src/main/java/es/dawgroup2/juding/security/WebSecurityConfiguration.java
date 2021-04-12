package es.dawgroup2.juding.security;

import es.dawgroup2.juding.auxTypes.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * PUBLIC PAGES
         */
        // IndexController
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/termsAndConditionsOfUse").permitAll();
        http.authorizeRequests().antMatchers("/cookiePolicy").permitAll();
        http.authorizeRequests().antMatchers("/signUp/*").permitAll();

        // IndexEmailController
        http.authorizeRequests().antMatchers("/index-email").permitAll();

        // ImageController
        http.authorizeRequests().antMatchers("/image/**").permitAll();

        // PostController
        http.authorizeRequests().antMatchers("/news/*").permitAll();

        // Error pages
        http.authorizeRequests().antMatchers("/error/403").permitAll();
        http.authorizeRequests().antMatchers("/error/404").permitAll();
        http.authorizeRequests().antMatchers("/error/500").permitAll();

        // PasswordRecoveryController
        http.authorizeRequests().antMatchers("/passwordRecovery/**").permitAll();

        // Resources (assets and templates)
        http.authorizeRequests().antMatchers("/static/**").permitAll();

        /*
         * PRIVATE PAGES
         */
        // ADMIN CONTROLLERS
        http.authorizeRequests().antMatchers("/admin/**").hasRole(Role.A.name());

        // LoggedInUserController
        http.authorizeRequests().antMatchers("/myHome").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/myProfile").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/myProfile/edit").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/ranking").hasAnyRole(Role.C.name(), Role.R.name());

        // ChartController
        http.authorizeRequests().antMatchers("/myCharts").hasAnyRole(Role.C.name(), Role.R.name());

        // CompetitionController
        http.authorizeRequests().antMatchers("/competition/*").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/competition/*/control").hasAnyRole(Role.R.name());

        // Login and logout
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("nickname");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/myHome");
        http.formLogin().failureUrl("/login/error");

        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");
    }
}