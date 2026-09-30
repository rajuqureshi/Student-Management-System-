package com.models.practiceproject.Controller;

import com.models.practiceproject.Dto.LoginRequest;
import com.models.practiceproject.Dto.LoginResponse;
import com.models.practiceproject.Entity.RefreshToken;
import com.models.practiceproject.Security.JwtService;
import com.models.practiceproject.Services.AuthenticationService;
import com.models.practiceproject.Services.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuothController {

    private final AuthenticationService authenticationService;
    public AuothController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(
                authenticationService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestParam
                                                      String refreshToken){
        return ResponseEntity.ok(
                authenticationService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String refreshToken){
        authenticationService.logout(refreshToken);
        return ResponseEntity.ok(
                "Logout Successful!"
        );
    }
}
