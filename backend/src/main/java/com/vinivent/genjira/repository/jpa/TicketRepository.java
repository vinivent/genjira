package com.vinivent.genjira.repository.jpa;

import com.vinivent.genjira.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
