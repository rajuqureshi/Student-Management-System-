package com.models.practiceproject.Services;


import com.models.practiceproject.Entity.OtpVerification;
import com.models.practiceproject.Repositories.OtpVerificationRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpVerificationServiceImpl implements OtpVerificationService {
    private final OtpVerificationRepository otpVerificationRepository;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();
    private PasswordEncoder passwordEncoder;
    private static final Integer MAX_ATTEMPTS = 3;

    public OtpVerificationServiceImpl(OtpVerificationRepository otpVerificationRepository,
                                      EmailService emailService,
                                      PasswordEncoder passwordEncoder) {

        this.otpVerificationRepository = otpVerificationRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void generateAndSendOtp(String email) {
        String otp = String.format("%06d", secureRandom.nextInt(1_000_000));
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(passwordEncoder.encode(otp));
        otpVerification.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        otpVerification.setVerified(false);
        otpVerification.setAttempt(0);
        otpVerificationRepository.save(otpVerification);
        emailService.emailOtpSending(email, otp);
    }

    @Override
    public void verifyOtp(String email, String otp) {
        OtpVerification otpVerification = otpVerificationRepository.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new RuntimeException("Otp Verification Not Found"));

        if(otpVerification.isVerified()){
            throw new RuntimeException("OTP Already Verified");
        }
        if (LocalDateTime.now().isAfter(otpVerification.getExpiredAt())) {
            throw new RuntimeException("OTP Expired");
        }

        if (otpVerification.getAttempt()>=MAX_ATTEMPTS) {
            throw new RuntimeException("Maximum number of attempts reached");
        }
        if (!passwordEncoder.matches(otp, otpVerification.getOtp())) {
            otpVerification.setAttempt(otpVerification.getAttempt() + 1);
            otpVerificationRepository.save(otpVerification);
            if (otpVerification.getAttempt()>=MAX_ATTEMPTS){
                throw new RuntimeException("Maximum number of attempts reached");
            }
            throw new RuntimeException("Invalid OTP");
        }


        otpVerification.setVerified(true);
        otpVerificationRepository.save(otpVerification);
    }
}
