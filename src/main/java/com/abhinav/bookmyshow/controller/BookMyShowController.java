package com.abhinav.bookmyshow.controller;

import com.abhinav.bookmyshow.model.City;
import com.abhinav.bookmyshow.model.Event;
import com.abhinav.bookmyshow.model.Show;
import com.abhinav.bookmyshow.model.Ticket;
import com.abhinav.bookmyshow.service.BookingService;
import com.abhinav.bookmyshow.service.CityService;
import com.abhinav.bookmyshow.service.EventService;
import com.abhinav.bookmyshow.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BookMyShowController {
    private final CityService cityService;
    private final EventService eventService;
    private final ShowService showService;
    private final BookingService bookingService;

    public BookMyShowController(CityService cityService,
                                EventService eventService,
                                ShowService showService,
                                BookingService bookingService) {
        this.cityService = cityService;
        this.eventService = eventService;
        this.showService = showService;
        this.bookingService = bookingService;
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        return cityService.listCities();
    }

    @GetMapping("/events/{cityId}")
    public List<Event> getEvents(@PathVariable Long cityId) {
        return eventService.getEventsByCity(cityId);
    }

    @GetMapping("/shows/{eventId}")
    public List<Show> getShows(@PathVariable Long eventId) {
        return showService.getShowsByEvent(eventId);
    }

    @PostMapping("/book/{showId}")
    public ResponseEntity<Ticket> book(@PathVariable Long showId) {
        Ticket ticket = bookingService.book(showId);
        if (ticket == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ticket);
    }
}
