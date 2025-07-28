package com.vinivent.genjira.controller;

import com.vinivent.genjira.dto.*;
import com.vinivent.genjira.model.User;
import com.vinivent.genjira.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserResponse dto = new UserResponse(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getName(),
                    user.getSituation(),
                    user.getAvatar(),
                    user.getCreatedAt()
            );

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@RequestBody EmailRequest request) {
        try {
            userService.resendVerificationEmail(request.email());
            return ResponseEntity.ok("Link de verificação reenviado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao reenviar verificação.");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailRequest request) {
        try {
            userService.sendResetPasswordEmail(request.email());
            return ResponseEntity.ok("Instruções de redefinição de senha enviadas.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar redefinição de senha.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request.token(), request.newPassword());
            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao redefinir senha.");
        }
    }

    @GetMapping("/reset-password/validate")
    public ResponseEntity<String> validateResetToken(@RequestParam String token) {
        try {
            boolean valid = userService.validateResetToken(token);
            return valid
                    ? ResponseEntity.ok("Token válido.")
                    : ResponseEntity.status(HttpStatus.GONE).body("Token expirado ou inválido.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao validar token.");
        }
    }
}
