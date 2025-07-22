package com.vinivent.genjira.repository.jpa.impl;

import com.vinivent.genjira.model.User;
import com.vinivent.genjira.repository.interfaces.IUserRepository;
import com.vinivent.genjira.repository.jpa.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserRepository userRepository;

    public UserRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
