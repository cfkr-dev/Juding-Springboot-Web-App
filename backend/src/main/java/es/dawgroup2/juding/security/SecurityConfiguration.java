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
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
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
        // Public pages
        // IndexController
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
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

        // Resources (assets and templates)
        http.authorizeRequests().antMatchers("/static/**").permitAll();

        // Private pages
        // LoggedInUserController
        http.authorizeRequests().antMatchers("/myHome").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/myProfile").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/myProfile/edit").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/ranking").hasAnyRole(Role.C.name(), Role.R.name());

        // ChartController
        http.authorizeRequests().antMatchers("/myCharts").hasAnyRole(Role.C.name(), Role.R.name());
        
        // PostController
        http.authorizeRequests().antMatchers("/admin/post/createNew").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/post/delete").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/post/edit/modify").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/post/edit/*").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/post/list").hasRole(Role.A.name());

        // UserController
        http.authorizeRequests().antMatchers("/admin/user/admitReferee/*").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/user/delete/*").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/user/edit/save").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/user/edit/*").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/user/list/*").hasRole(Role.A.name());

        // PasswordRecoveryController
        http.authorizeRequests().antMatchers("/passwordRecovery/1").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/passwordRecovery/1/*").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/passwordRecovery/2").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/passwordRecovery/2/*").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/passwordRecovery/3").hasAnyRole(Role.C.name(), Role.R.name());
        http.authorizeRequests().antMatchers("/passwordRecovery/3/*").hasAnyRole(Role.C.name(), Role.R.name());

        // FightController
        http.authorizeRequests().antMatchers("/admin/fight/list").hasRole(Role.A.name());

        // CompetitionController
        http.authorizeRequests().antMatchers("/admin/competition/deleteCompetition/*").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/competition/edit").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/competition/edit/*").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/competition/list").hasRole(Role.A.name());
        http.authorizeRequests().antMatchers("/admin/competition/newCompetition").hasRole(Role.A.name());
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
