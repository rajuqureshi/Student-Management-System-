package com.models.practiceproject.Repositories;

import com.models.practiceproject.Entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRespository extends CrudRepository<RefreshToken, Long> {

    public Optional<RefreshToken> findByToken(String token);
}
