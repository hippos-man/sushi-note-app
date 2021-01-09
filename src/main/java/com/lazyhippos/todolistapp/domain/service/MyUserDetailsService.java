package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.repository.UserJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    MyUserDetailsService(UserJpaRepository userJpaRepository){
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userId) throws UsernameNotFoundException {
        Optional<Users> usersOptional = userJpaRepository.findById(userId);
        if(!usersOptional.isPresent()){
            throw new UsernameNotFoundException("No user found with user id: " + userId);
        }
        Users user = usersOptional.get();
        String role =user.getRoleName().name();

        List<GrantedAuthority> grantedList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantedList.add(authority);

        UserDetails userDetails = (UserDetails)
                new User(user.getUserId(), user.getPassword(), user.getEnabled(), true, true, true, grantedList);
        return userDetails;
    }
}