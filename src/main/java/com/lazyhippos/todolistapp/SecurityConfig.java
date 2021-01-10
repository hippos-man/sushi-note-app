package com.lazyhippos.todolistapp;

import com.lazyhippos.todolistapp.domain.service.MyUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder, MyUserDetailsService myUserDetailsService){
        this.passwordEncoder = passwordEncoder;
        this.myUserDetailsService = myUserDetailsService;
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers(
                        "/css/**",
                        "/images/**",
                        "/user/signup",
                        "/user/register",
                        "/registrationConfirm",
                        "/result",
                        "/",
                        "/categories/**",
                        "/s/**",
                        "/api/v1/uploads/images/**",
                        "/api/v1/articles/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .loginPage("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception{
        managerBuilder.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
