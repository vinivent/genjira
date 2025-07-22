package com.vinivent.genjira.repository.jpa;

import com.vinivent.genjira.model.User;
import com.vinivent.genjira.model.UserVerified;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserVerifiedRepository extends JpaRepository<UserVerified, UUID> {
    Optional<UserVerified> findByUser(User user);
    Optional<UserVerified> findByVerificationToken(String token);
}
