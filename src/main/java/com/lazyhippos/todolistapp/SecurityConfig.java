package com.lazyhippos.todolistapp;

import com.lazyhippos.todolistapp.domain.service.TaskUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .antMatchers("/css/**", "/images/**","/user/sign-up", "/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/to-do/list", true)
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
