package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.UserRequest;
import com.lazyhippos.todolistapp.application.resource.UserUpdateRequest;
import com.lazyhippos.todolistapp.domain.model.RoleName;
import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.model.VerificationToken;
import com.lazyhippos.todolistapp.domain.repository.TokenJpaRepository;
import com.lazyhippos.todolistapp.domain.repository.UserJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final TokenJpaRepository tokenJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    UserService(UserJpaRepository userJpaRepository, TokenJpaRepository tokenJpaRepository, PasswordEncoder passwordEncoder){
        this.userJpaRepository = userJpaRepository;
        this.tokenJpaRepository = tokenJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users register(UserRequest request, LocalDateTime now){
        String encryptedPassword = passwordEncoder
                .encode(request.getPassword());
        Users newUser = new Users(
                request.getUserId(),
                request.getDisplayName(),
                request.getEmailAddress(),
                encryptedPassword,
                null,
                true,
                now,
                now,
                RoleName.USER
        );
        return userJpaRepository.save(newUser);
    }

    public void update (UserUpdateRequest request, LocalDateTime now) {
            userJpaRepository.updateUserProfile(
                    request.getUserId(),
                    request.getDisplayName(),
                    request.getEmailAddress(),
                    request.getImageId(),
                    now
            );
    }

    public Users retrieveAuthorProfile(String userId) {
        return userJpaRepository.getOne(userId);
    }

    public Boolean isUserIdExist(String userId) {
        return userJpaRepository.existsById(userId);
    }

    public Map<String, String> retrieveDisplayNameAndUserIdByUserIds (List<String> userIds) {
        List<Users> users = userJpaRepository.findAllByUserIdIn(userIds);
        Map<String, String> userMap = users.stream().collect(
                Collectors.toMap(Users::getUserId, Users::getDisplayName));
        return userMap;
    }

    public Map<String, Long> retrieveImageIdAndUserIdByUserIds(List<String> userIds) {
        List<Users> users = userJpaRepository.findAllByUserIdIn(userIds);
        Map<String, Long> userMap = users.stream().collect(
                Collectors.toMap(Users::getUserId, Users::getImageId));
        return userMap;
    }

    public VerificationToken createVerificationTokenForUser(final Users user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        return tokenJpaRepository.save(myToken);
    }

    public String validateVerificationToken(String verificationToken) {
        final VerificationToken token = tokenJpaRepository.findByToken(verificationToken);
        if (token == null) {
            return TOKEN_INVALID;
        }

        final Users user = token.getUser();
        final Calendar calendar = Calendar.getInstance();

        if ((token.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            tokenJpaRepository.delete(token);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        userJpaRepository.save(user);
        return TOKEN_VALID;
    }

    public Users getUser(final String verificationToken) {
        final VerificationToken token = tokenJpaRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

}
