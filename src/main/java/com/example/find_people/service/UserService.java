package com.example.find_people.service;

import com.example.find_people.dto.UserResponse;
import com.example.find_people.entity.User;
import com.example.find_people.entity.enums.UserStatus;
import com.example.find_people.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FirebaseAuth firebaseAuth;

    // 로그인 & 회원가입
    public UserResponse login(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            Optional<User> existingUser = userRepository.findByUid(uid);

            if(existingUser.isEmpty()) {
                // 회원가입
                User newUser = new User();
                newUser.setUid(uid);
                newUser.setEmail(decodedToken.getEmail());
                newUser.setStatus(UserStatus.ACTIVE.toString());
                newUser.setName("새로운 유저");
                userRepository.save(newUser);

                return UserResponse.builder()
                        .uid(uid)
                        .email(newUser.getEmail())
                        .build();
            }

            return UserResponse.builder()
                    .uid(uid)
                    .email(existingUser.get().getEmail())
                    .build();

        } catch (FirebaseAuthException e) {
            throw new RuntimeException("firebase user login 실패 ", e);
        }
    }

}
