package com.lazyhippos.todolistapp;

import com.lazyhippos.todolistapp.domain.service.TaskUserDetailsService;
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

    private final TaskUserDetailsService taskUserDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder,TaskUserDetailsService taskUserDetailsService){
        this.passwordEncoder = passwordEncoder;
        this.taskUserDetailsService = taskUserDetailsService;
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
                        "/",
                        "/categories/**",
                        "/s/**",
                        "/api/v1/uploads/images/**").permitAll()
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
        managerBuilder.userDetailsService(taskUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
