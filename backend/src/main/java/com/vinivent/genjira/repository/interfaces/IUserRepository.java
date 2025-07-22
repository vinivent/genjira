package com.vinivent.genjira.repository.interfaces;

import com.vinivent.genjira.model.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    void deleteById(UUID id);
}
