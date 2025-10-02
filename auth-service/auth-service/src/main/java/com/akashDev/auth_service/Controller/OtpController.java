package com.akashDev.auth_service.Controller;

import com.akashDev.auth_service.Service.OtpService;
import com.akashDev.auth_service.Service.UserService;
import com.akashDev.auth_service.dto.LoginDto;
import com.akashDev.auth_service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;
    private final UserService userService;
    private UserDto userDto;

    @GetMapping("/")
    public String home() {
        return "Welcome! You are logged in via Google OAuth2.";
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        otpService.generateOtp(email);
        return ResponseEntity.ok("OTP sent successfully to " + email);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean verified = otpService.verifyOtp(email, otp);
        if (verified) {
            return ResponseEntity.ok("OTP verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
//        System.out.println("working");
        String result = userService.createUser(
                userDto.getFname(),
                userDto.getLname(),
                userDto.getDob(),
                userDto.getEmail(),
                userDto.getPassword()
        );
        if (result.startsWith("User already exists")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String result = userService.login(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
        if (result.equals("Login successful")) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

}
