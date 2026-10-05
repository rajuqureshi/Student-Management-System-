package com.models.practiceproject.Services;

public interface OtpVerificationService {

    void generateAndSendOtp(String email);
    void verifyOtp(String email, String otp);
}
