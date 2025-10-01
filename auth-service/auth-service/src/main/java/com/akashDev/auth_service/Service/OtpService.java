package com.akashDev.auth_service.Service;

import com.akashDev.auth_service.Entity.OtpVerification;
import com.akashDev.auth_service.Repo.OtpVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpVerificationRepository repository;
    private final JavaMailSender mailSender;

    public void generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpVerification otpEntity = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        repository.save(otpEntity);

        // Send Email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + " (valid for 5 minutes)");
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String otp) {
        return repository.findByEmailAndOtp(email, otp)
                .filter(o -> !o.isVerified() && o.getExpiryTime().isAfter(LocalDateTime.now()))
                .map(o -> {
                    o.setVerified(true);
                    repository.save(o);
                    return true;
                })
                .orElse(false);
    }
}