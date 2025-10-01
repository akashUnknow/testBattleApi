package com.akashDev.auth_service.Service;

import com.akashDev.auth_service.Entity.UserEntity;
import com.akashDev.auth_service.Repo.UserData;
import com.akashDev.auth_service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserData repo;

    // Non-static method
    public String createUser(String Fname, String Lname, String dob, String email, String password) {
        if(repo.existsByEmail(email)){
            return "User already exists with email: " + email;
        }
        UserEntity user = UserEntity.builder()
                .Fname(Fname)
                .Lname(Lname)
                .dob(dob)
                .email(email)
                .password(password)
                .build();
        repo.save(user);
        return "User registered successfully!";
    }

    public String login(String email, String password) {
        Optional<UserEntity> userOtp=repo.findByEmail(email);

        if(userOtp.isEmpty()){
            return "User not found";
        }
        UserEntity user=userOtp.get();
        System.out.println("user : "+user);
        if(!user.getPassword().equals(password)){
            return "Invalid password";
        }
        return "Login successful";
    }
}
