package com.vinivent.genjira.service.interfaces;

import com.vinivent.genjira.dto.RegisterRequest;

public interface IUserService {

    void registerUser(RegisterRequest request);

    String verifyUser(String token);

    void resendVerificationEmail(String email);

    void sendResetPasswordEmail(String email);

    void resetPassword(String token, String newPassword);

    boolean validateResetToken(String token);
}
