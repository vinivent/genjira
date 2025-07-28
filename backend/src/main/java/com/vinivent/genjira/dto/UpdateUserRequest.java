package com.vinivent.genjira.dto;

public record UpdateUserRequest(
       String username,
       String name,
       String userDescription,
       String avatar,
       String password
) {
}
