package com.abhinav.bookmyshow.service;

import com.abhinav.bookmyshow.model.Show;
import com.abhinav.bookmyshow.model.Ticket;
import com.abhinav.bookmyshow.repository.ShowRepository;
import com.abhinav.bookmyshow.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;

    public BookingService(ShowRepository showRepository, TicketRepository ticketRepository) {
        this.showRepository = showRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket book(Long showId) {
        Show show = showRepository.findById(showId);
        if (show != null && show.bookSeat()) {
            return ticketRepository.save(showId);
        }
        return null;
    }
}
