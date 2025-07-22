package com.vinivent.genjira.service;

import com.vinivent.genjira.repository.interfaces.IPasswordResetTokenRepository;
import com.vinivent.genjira.model.PasswordResetToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TokenCleanupService {

    private final IPasswordResetTokenRepository resetTokenRepository;

    public TokenCleanupService(IPasswordResetTokenRepository resetTokenRepository) {
        this.resetTokenRepository = resetTokenRepository;
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanExpiredTokens() {

        List<PasswordResetToken> expiredTokens = resetTokenRepository.findAll().stream()
                .filter(token -> token.getExpiration().isBefore(Instant.now()))  // Verifica se o token expirou
                .toList();

        for (PasswordResetToken token : expiredTokens) {
            resetTokenRepository.deleteByToken(token.getToken());

            System.out.println("Removed expired token: " + token.getToken());
        }
    }
}
