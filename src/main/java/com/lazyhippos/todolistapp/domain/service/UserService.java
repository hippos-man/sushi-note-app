package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.UserRequest;
import com.lazyhippos.todolistapp.domain.model.RoleName;
import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;

    UserService(UserJpaRepository userJpaRepository){
        this.userJpaRepository = userJpaRepository;
    }

    public void register(UserRequest request, LocalDateTime now){
        Users user = new Users(
                request.getUserId(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                true,
                now,
                now,
                RoleName.USER
        );
        userJpaRepository.save(user);
    }
}
