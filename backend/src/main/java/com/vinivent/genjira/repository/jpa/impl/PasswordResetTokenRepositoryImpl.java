package com.vinivent.genjira.repository.jpa.impl;

import com.vinivent.genjira.model.PasswordResetToken;
import com.vinivent.genjira.model.User;
import com.vinivent.genjira.repository.interfaces.IPasswordResetTokenRepository;
import com.vinivent.genjira.repository.jpa.PasswordResetTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PasswordResetTokenRepositoryImpl implements IPasswordResetTokenRepository {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenRepositoryImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void deleteByUser(User user) {
        passwordResetTokenRepository.deleteByUser(user);
    }

    @Override
    public void deleteByToken(String token) {
        passwordResetTokenRepository.deleteByToken(token);
    }

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        return passwordResetTokenRepository.save(token);
    }

    @Override
    public List<PasswordResetToken> findAll() {
        return passwordResetTokenRepository.findAll();
    }
}
