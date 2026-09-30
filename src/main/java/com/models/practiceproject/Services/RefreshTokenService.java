package com.models.practiceproject.Services;

import com.models.practiceproject.Entity.RefreshToken;
import com.models.practiceproject.Entity.Student;
import com.models.practiceproject.Exception.InvalidRefreshTokenException;
import com.models.practiceproject.Repositories.RefreshTokenRespository;
import com.models.practiceproject.Repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRespository refreshTokenRespository;
    private final StudentRepository studentRepository;

    @Value("${jwt.expiration-days}")
    private Long refreshTokenExpiryDays;

    public RefreshToken createRefreshToken(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(()-> new InvalidRefreshTokenException("Student with email " + email + " not found"));
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setStudent(student);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(refreshTokenExpiryDays));
        refreshToken.setRevoked(false);
        return refreshTokenRespository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRespository.findByToken(token)
                .orElseThrow(()-> new InvalidRefreshTokenException("Refresh token " + token + " not found"));
        if (refreshToken.isRevoked()){
            throw new RuntimeException("Refresh token is already revoked");
        }
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Refresh token is expired");
        }
        return refreshToken;
    }

    @Transactional
    public RefreshToken rotateRefreshToken(RefreshToken oldRefreshToken) {
//        oldRefreshToken.setExpiryDate(LocalDateTime.now().minusDays(1));
        oldRefreshToken.setRevoked(true);
        refreshTokenRespository.save(oldRefreshToken);
        return createRefreshToken(
                oldRefreshToken.getStudent().getEmail()
        );
    }

    public void revokedRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRespository.findByToken(token)
                .orElseThrow(()-> new InvalidRefreshTokenException("Refresh token " + token + " not found"));

        refreshToken.setRevoked(true);
        refreshTokenRespository.save(refreshToken);
    }
}
