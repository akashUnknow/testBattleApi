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
        if(!user.getPassword().equals(password)){
            return "Invalid password";
        }
        return "Login successful";
    }

    public UserEntity createOrGetGoogleUser(String fname,String lname, String email) {
        Optional<UserEntity> existingUser = repo.findByEmail(email);
        if(existingUser.isPresent()) {
            return existingUser.get(); // user exists, return
        }



        UserEntity newUser = UserEntity.builder()
                .Fname(fname)
                .Lname(lname)
                .email(email)
                .dob("")       // optional, can be null
                .password("")  // blank since Google login
                .build();

        repo.save(newUser);
        return newUser;
    }


    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
