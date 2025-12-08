package com.abhinav.bookmyshow.web;

import com.abhinav.bookmyshow.model.City;
import com.abhinav.bookmyshow.model.Event;
import com.abhinav.bookmyshow.model.EventType;
import com.abhinav.bookmyshow.model.VenueShowcase;
import com.abhinav.bookmyshow.service.InMemoryCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final InMemoryCatalogService catalogService;

    public CatalogController(InMemoryCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/cities")
    public List<City> listCities() {
        return catalogService.getCities();
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> listEvents(@RequestParam String city,
                                                  @RequestParam(required = false) String q,
                                                  @RequestParam(required = false) String type) {
        Optional<EventType> parsedType = parseType(type);
        List<Event> events = catalogService.getEventsForCity(city, parsedType, q);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Event>> listMovies(@RequestParam String city,
                                                  @RequestParam(required = false) String q) {
        List<Event> movies = catalogService.getEventsForCity(city, Optional.of(EventType.MOVIE), q);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/movies/{movieId}/venues")
    public ResponseEntity<List<VenueShowcase>> venuesForMovie(@PathVariable String movieId,
                                                              @RequestParam String city) {
        Optional<Event> event = catalogService.getEventById(movieId);
        if (event.isEmpty() || event.get().type() != EventType.MOVIE) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(catalogService.getVenuesForEvent(movieId, city));
    }

    private Optional<EventType> parseType(String type) {
        if (type == null || type.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(EventType.valueOf(type.toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
