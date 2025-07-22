package com.vinivent.genjira.repository.interfaces;

import com.vinivent.genjira.model.Organization;

import java.util.Optional;
import java.util.UUID;

public interface IOrganizationRepository {
    Optional<Organization> findById(UUID id);
    Organization save(Organization organization);
    void deleteById(UUID id);
}
