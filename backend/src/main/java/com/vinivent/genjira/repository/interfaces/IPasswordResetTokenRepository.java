package com.vinivent.genjira.repository.interfaces;

import com.vinivent.genjira.model.PasswordResetToken;
import com.vinivent.genjira.model.User;

import java.util.List;
import java.util.Optional;

public interface IPasswordResetTokenRepository {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user);
    void deleteByToken(String token);
    PasswordResetToken save(PasswordResetToken token);
    List<PasswordResetToken> findAll();
}
