package com.vinivent.genjira.repository.interfaces;

import com.vinivent.genjira.model.User;
import com.vinivent.genjira.model.UserVerified;

import java.util.Optional;

public interface IUserVerifiedRepository {
    Optional<UserVerified> findByUser(User user);
    Optional<UserVerified> findByVerificationToken(String token);
    UserVerified save(UserVerified verification);
    void delete(UserVerified verification);
}
