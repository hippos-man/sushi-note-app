package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.repository.UserJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    TaskUserDetailsService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder){
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder =passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Users> user = userJpaRepository.findById(userId);
        if(!user.isPresent()){
            System.out.println("User Not Found in DB");
            throw new UsernameNotFoundException("UserId : " + userId + " was not found in the database");
        }
        System.out.println("His/Her First name is : "+ user.get().getFirstName());
        // For Authority List
        List<GrantedAuthority> grantedList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        grantedList.add(authority);
        UserDetails userDetails = (UserDetails)
                new User(user.get().getUserId(), user.get().getPassword(),grantedList);

        return userDetails;
    }
}
