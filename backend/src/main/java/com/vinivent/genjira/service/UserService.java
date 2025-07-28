package com.vinivent.genjira.service;

import com.vinivent.genjira.dto.RegisterRequest;
import com.vinivent.genjira.enums.UserSituation;
import com.vinivent.genjira.exception.EmailAlreadyUsedException;
import com.vinivent.genjira.model.PasswordResetToken;
import com.vinivent.genjira.model.User;
import com.vinivent.genjira.model.UserVerified;
import com.vinivent.genjira.repository.PasswordResetTokenRepository;
import com.vinivent.genjira.repository.UserRepository;
import com.vinivent.genjira.repository.UserVerifiedRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserVerifiedRepository userVerifiedRepository;
    private final PasswordResetTokenRepository resetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public UserService(UserRepository userRepository, UserVerifiedRepository userVerifiedRepository,
                       PasswordResetTokenRepository resetTokenRepository, PasswordEncoder passwordEncoder,
                       MailService mailService) {
        this.userRepository = userRepository;
        this.userVerifiedRepository = userVerifiedRepository;
        this.resetTokenRepository = resetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public void registerUser(RegisterRequest request) {
        userRepository.findByEmail(request.email().trim().toLowerCase())
                .ifPresent(user -> {
                    throw new EmailAlreadyUsedException("Esse e-mail já está em uso.");
                });

        User user = new User();
        user.setUsername(request.username().trim().toLowerCase());
        user.setEmail(request.email().trim().toLowerCase());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setSituation(UserSituation.PENDING);
        userRepository.save(user);

        UserVerified verification = createAndSaveVerificationToken(user);
        userVerifiedRepository.save(verification);

        String url = "https://genjira.com/verify/" + verification.getVerificationToken();
        mailService.sendAccountVerificationEmail(user.getEmail(), url, user.getUsername());
    }

    public String verifyUser(String token) {
        Optional<UserVerified> optionalUserVerified = userVerifiedRepository.findByVerificationToken(token);

        if (optionalUserVerified.isEmpty())
            return "Invalid or expired token.";

        UserVerified verification = optionalUserVerified.get();

        if (verification.isExpired())
            return "Token expired. Request a new one.";

        User user = verification.getUser();
        user.setSituation(UserSituation.ACTIVE);
        userRepository.save(user);
        userVerifiedRepository.delete(verification);

        return "Account successfully verified!";
    }

    public void resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Optional<UserVerified> existing = userVerifiedRepository.findByUser(user);

        if (existing.isPresent() && !existing.get().isExpired()) {
            throw new IllegalArgumentException("You already have a valid link. Check your email.");
        }

        existing.ifPresent(userVerifiedRepository::delete);

        UserVerified newVerification = createAndSaveVerificationToken(user);

        String url = "https://genjira.com/verify/" + newVerification.getVerificationToken();
        mailService.sendResendVerificationEmail(user.getEmail(), url, user.getUsername());
    }


    public void sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        resetTokenRepository.deleteByUser(user); // limpa tokens antigos

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiration(Instant.now().plusSeconds(900)); // 15 min
        resetTokenRepository.save(token);

        String resetUrl = "https://genjira.com/reset-password/" + token.getToken();
        mailService.sendPasswordResetEmail(user.getEmail(), resetUrl, user.getUsername());
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token."));

        if (resetToken.getExpiration().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Token expired. Please request a new one.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));  // Criptografa a nova senha
        userRepository.save(user);

        resetTokenRepository.deleteByToken(resetToken.getToken());
    }

    public boolean validateResetToken(String token) {
        Optional<PasswordResetToken> resetToken = resetTokenRepository.findByToken(token);
        return resetToken.isPresent() && resetToken.get().getExpiration().isAfter(Instant.now());
    }

    private UserVerified createAndSaveVerificationToken(User user) {
        UserVerified verification = new UserVerified();
        verification.setUser(user);
        verification.setVerificationToken(UUID.randomUUID().toString());
        verification.setCreatedAt(new Date());
        verification.setExpiresAt(new Date(System.currentTimeMillis() + (long) 900000));

        return userVerifiedRepository.save(verification);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
