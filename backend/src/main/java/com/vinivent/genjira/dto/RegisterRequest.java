package com.vinivent.genjira.dto;

public record RegisterRequest(
        String username,
        String email,
        String password,
        String name
) {}
