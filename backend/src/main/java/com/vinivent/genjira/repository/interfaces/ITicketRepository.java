package com.vinivent.genjira.repository.interfaces;

import com.vinivent.genjira.model.Ticket;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ITicketRepository {
    Optional<Ticket> findById(UUID id);
    Ticket save(Ticket ticket);
    void deleteById(UUID id);
    List<Ticket> findAll();
}
