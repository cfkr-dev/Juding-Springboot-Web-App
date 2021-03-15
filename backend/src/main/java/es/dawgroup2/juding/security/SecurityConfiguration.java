package es.dawgroup2.juding.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
