package com.vinivent.genjira.repository;

import com.vinivent.genjira.model.UserVerified;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserVerifiedRepository extends JpaRepository<UserVerified, UUID> {


}
