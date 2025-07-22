package com.vinivent.genjira.repository.jpa.impl;

import com.vinivent.genjira.model.Organization;
import com.vinivent.genjira.repository.interfaces.IOrganizationRepository;
import com.vinivent.genjira.repository.jpa.OrganizationRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class OrganizationRepositoryImpl implements IOrganizationRepository {

    private final OrganizationRepository organizationRepository;

    public OrganizationRepositoryImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<Organization> findById(UUID id) {
        return organizationRepository.findById(id);
    }

    @Override
    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public void deleteById(UUID id) {
        organizationRepository.deleteById(id);
    }
}
