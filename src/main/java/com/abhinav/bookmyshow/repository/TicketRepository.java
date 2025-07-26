package com.abhinav.bookmyshow.repository;

import com.abhinav.bookmyshow.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TicketRepository {
    private final Map<Long, Ticket> tickets = new HashMap<>();
    private long idCounter = 1;

    public synchronized Ticket save(Long showId) {
        Ticket ticket = new Ticket(idCounter++, showId);
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return new ArrayList<>(tickets.values());
    }
}
