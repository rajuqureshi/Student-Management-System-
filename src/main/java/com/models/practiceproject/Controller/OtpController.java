package com.models.practiceproject.Controller;

import com.models.practiceproject.Services.EmailService;
import com.models.practiceproject.Services.OtpVerificationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/otp")
public class OtpController {
    private OtpVerificationService otpVerificationService;
    private EmailService emailService;

    public OtpController(OtpVerificationService otpVerificationService, EmailService emailService) {
        this.otpVerificationService = otpVerificationService;
        this.emailService = emailService;
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email){
        otpVerificationService.generateAndSendOtp(email);
        return ResponseEntity.ok().body("Otp send successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email,@RequestParam String otp){
        otpVerificationService.verifyOtp(email,otp);
        return ResponseEntity.ok().body("Otp verified successfully");
    }
}
