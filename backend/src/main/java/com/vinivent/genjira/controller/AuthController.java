package com.vinivent.genjira.controller;

import com.vinivent.genjira.dto.LoginRequest;
import com.vinivent.genjira.dto.RegisterRequest;
import com.vinivent.genjira.service.UserService;
import com.vinivent.genjira.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.username(), request.password());
        authenticationManager.authenticate(authentication);
        String token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Usuário registrado com sucesso. Verifique seu e-mail.");
    }

    @GetMapping("/verify/{uuid}")
    public ResponseEntity<String> verify(@PathVariable("uuid") String token) {
        try {
            String result = userService.verifyUser(token);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao verificar usuário.");
        }
    }
}
