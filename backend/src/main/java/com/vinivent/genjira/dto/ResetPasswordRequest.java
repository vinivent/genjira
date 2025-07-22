package com.vinivent.genjira.dto;

public record ResetPasswordRequest(
        String token,
        String newPassword
) {}
