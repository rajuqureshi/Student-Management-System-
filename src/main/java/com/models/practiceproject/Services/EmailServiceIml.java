package com.models.practiceproject.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceIml implements EmailService {
    private final JavaMailSender mailSender;


    @Override
    public void emailOtpSending(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(
                "Student Management System - OTP Varification "
        );
        message.setText(
                "Your OTP has been sent to " + otp +
                        "\n\nand this is valid for 5 minutes."
        );
        mailSender.send(message);
    }
}
