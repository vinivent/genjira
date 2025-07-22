package com.vinivent.genjira.repository.jpa.impl;

import com.vinivent.genjira.model.Ticket;
import com.vinivent.genjira.repository.interfaces.ITicketRepository;
import com.vinivent.genjira.repository.jpa.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public class TicketRepositoryImpl implements ITicketRepository {

    private final TicketRepository ticketRepository;

    public TicketRepositoryImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(UUID id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
