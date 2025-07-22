package com.vinivent.genjira.repository.jpa.impl;

import com.vinivent.genjira.model.User;
import com.vinivent.genjira.model.UserVerified;
import com.vinivent.genjira.repository.interfaces.IUserVerifiedRepository;
import com.vinivent.genjira.repository.jpa.UserVerifiedRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserVerifiedRepositoryImpl implements IUserVerifiedRepository {

    private final UserVerifiedRepository userVerifiedRepository;

    public UserVerifiedRepositoryImpl(UserVerifiedRepository userVerifiedRepository) {
        this.userVerifiedRepository = userVerifiedRepository;
    }

    @Override
    public Optional<UserVerified> findByUser(User user) {
        return userVerifiedRepository.findByUser(user);
    }

    @Override
    public Optional<UserVerified> findByVerificationToken(String token) {
        return userVerifiedRepository.findByVerificationToken(token);
    }

    @Override
    public UserVerified save(UserVerified verification) {
        return userVerifiedRepository.save(verification);
    }

    @Override
    public void delete(UserVerified verification) {
        userVerifiedRepository.delete(verification);
    }
}
