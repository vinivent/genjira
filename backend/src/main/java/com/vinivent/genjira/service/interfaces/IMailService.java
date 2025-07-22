package com.vinivent.genjira.service.interfaces;

import java.util.Map;

public interface IMailService {

    void sendEmail(String recipientEmail, String subject, String templateName, Map<String, Object> variables);

    void sendAccountVerificationEmail(String email, String verificationUrl, String username);

    void sendPasswordResetEmail(String email, String resetUrl, String username);

    void sendResendVerificationEmail(String email, String verificationUrl, String username);
}
