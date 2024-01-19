package com.kyura.message.services;

import com.kyura.message.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
	Optional<RefreshToken> findByToken(String token);
	RefreshToken createRefreshToken(Long userId);
	RefreshToken verifyExpiration(RefreshToken token);
	int deleteByUserId(Long userId);
}
