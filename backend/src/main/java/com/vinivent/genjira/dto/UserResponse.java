package com.vinivent.genjira.dto;

import com.vinivent.genjira.enums.UserSituation;

import java.util.Date;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String name,
        UserSituation situation,
        String avatar,
        Date createdAt
) {}
