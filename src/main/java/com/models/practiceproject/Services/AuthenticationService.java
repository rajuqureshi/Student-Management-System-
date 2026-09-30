package com.models.practiceproject.Services;


import com.models.practiceproject.Dto.LoginRequest;
import com.models.practiceproject.Dto.LoginResponse;
import com.models.practiceproject.Entity.RefreshToken;
import com.models.practiceproject.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;


    public LoginResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        return new LoginResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    public LoginResponse refreshToken(String refreshToken){
        RefreshToken verifiedToken = refreshTokenService.verifyRefreshToken(
                refreshToken
        );
        UserDetails userDetails = User.builder()
                .username(
                        verifiedToken.getStudent().getEmail()
                )
                .password(
                        verifiedToken.getStudent().getPassword()
                )
                .roles(
                        verifiedToken.getStudent().getRole()
                )
                .build();

        String newAccessToken = jwtService.generateToken(userDetails);
        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(
                verifiedToken
        );
        return
                new LoginResponse(
                        newAccessToken,
                        newRefreshToken.getToken()
                );
    }
    public void logout( String refreshToken){
        refreshTokenService.revokedRefreshToken(refreshToken);
    }
}
