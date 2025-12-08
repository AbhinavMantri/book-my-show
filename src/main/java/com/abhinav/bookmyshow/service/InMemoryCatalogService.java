package com.abhinav.bookmyshow.service;

import com.abhinav.bookmyshow.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InMemoryCatalogService {

    private final Map<String, City> cities;
    private final Map<String, Event> events;
    private final List<Venue> venues;

    public InMemoryCatalogService() {
        this.cities = loadCities();
        this.events = loadEvents();
        this.venues = loadVenues();
    }

    public List<City> getCities() {
        return cities.values().stream()
                .sorted(Comparator.comparing(City::name))
                .toList();
    }

    public List<Event> getEventsForCity(String cityCode, Optional<EventType> type, String query) {
        String loweredQuery = query == null ? null : query.toLowerCase(Locale.ROOT);
        Set<String> availableEventIds = venues.stream()
                .filter(venue -> venue.city().equalsIgnoreCase(cityCode))
                .flatMap(venue -> venue.screens().stream())
                .flatMap(screen -> screen.shows().stream())
                .map(ShowTime::eventId)
                .collect(Collectors.toSet());

        return events.values().stream()
                .filter(event -> availableEventIds.contains(event.id()))
                .filter(event -> type.map(t -> event.type() == t).orElse(true))
                .filter(event -> loweredQuery == null || event.title().toLowerCase(Locale.ROOT).contains(loweredQuery))
                .sorted(Comparator.comparing(Event::title))
                .toList();
    }

    public Optional<Event> getEventById(String eventId) {
        return Optional.ofNullable(events.get(eventId));
    }

    public List<VenueShowcase> getVenuesForEvent(String eventId, String cityCode) {
        return venues.stream()
                .filter(venue -> venue.city().equalsIgnoreCase(cityCode))
                .map(venue -> buildVenueShowcase(venue, eventId))
                .filter(showcase -> showcase.screens().stream().anyMatch(screen -> !screen.shows().isEmpty()))
                .toList();
    }

    private VenueShowcase buildVenueShowcase(Venue venue, String eventId) {
        List<Screen> filteredScreens = venue.screens().stream()
                .map(screen -> new Screen(screen.name(), screen.shows().stream()
                        .filter(show -> show.eventId().equals(eventId))
                        .toList()))
                .filter(screen -> !screen.shows().isEmpty())
                .toList();
        return new VenueShowcase(venue.id(), venue.name(), venue.address(), filteredScreens);
    }

    private Map<String, City> loadCities() {
        return List.of(
                new City("blr", "Bengaluru"),
                new City("mum", "Mumbai"),
                new City("del", "Delhi NCR"),
                new City("hyd", "Hyderabad")
        ).stream().collect(Collectors.toMap(City::code, city -> city));
    }

    private Map<String, Event> loadEvents() {
        List<Event> seededEvents = List.of(
                new Event("mv-kgf2", "KGF Chapter 2", EventType.MOVIE, "Kannada", "Action", 168,
                        List.of("IMAX", "4DX"), "Rocky returns to guard Kolar Gold Fields."),
                new Event("mv-jawan", "Jawan", EventType.MOVIE, "Hindi", "Action", 169,
                        List.of("IMAX", "2D"), "An officer sets out on a personal vendetta."),
                new Event("mv-barbie", "Barbie", EventType.MOVIE, "English", "Comedy", 114,
                        List.of("2D"), "Barbie steps out of Barbieland for an adventure."),
                new Event("evt-arijit", "Arijit Singh Live", EventType.EVENT, "Hindi", "Concert", 120,
                        List.of("Music", "Live"), "Soulful evening with Arijit Singh."),
                new Event("evt-standup", "Saturday Standup", EventType.EVENT, "English", "Comedy", 90,
                        List.of("Comedy", "Club"), "A curated line-up of city comics."),
                new Event("evt-theatre", "Broadway Classics", EventType.EVENT, "English", "Theatre", 140,
                        List.of("Drama"), "Best of Broadway performances.")
        );
        return seededEvents.stream().collect(Collectors.toMap(Event::id, event -> event));
    }

    private List<Venue> loadVenues() {
        return List.of(
                new Venue("v-inox-1", "INOX Mantri Square", "blr", "Malleshwaram, Bengaluru",
                        List.of(
                                new Screen("Screen 1", List.of(
                                        new ShowTime("mv-kgf2", "10:00 AM", "Kannada", "IMAX", BigDecimal.valueOf(450)),
                                        new ShowTime("mv-jawan", "02:00 PM", "Hindi", "IMAX", BigDecimal.valueOf(480)),
                                        new ShowTime("mv-barbie", "06:00 PM", "English", "IMAX", BigDecimal.valueOf(500))
                                )),
                                new Screen("Screen 2", List.of(
                                        new ShowTime("mv-kgf2", "11:30 AM", "Hindi", "4DX", BigDecimal.valueOf(430)),
                                        new ShowTime("mv-jawan", "05:00 PM", "Hindi", "4DX", BigDecimal.valueOf(470))
                                ))
                        )),
                new Venue("v-pvr-1", "PVR Koramangala", "blr", "Koramangala, Bengaluru",
                        List.of(
                                new Screen("Audi 1", List.of(
                                        new ShowTime("mv-jawan", "09:30 AM", "Hindi", "2D", BigDecimal.valueOf(320)),
                                        new ShowTime("mv-barbie", "01:45 PM", "English", "2D", BigDecimal.valueOf(350))
                                )),
                                new Screen("Audi 2", List.of(
                                        new ShowTime("mv-kgf2", "04:30 PM", "Kannada", "2D", BigDecimal.valueOf(300)),
                                        new ShowTime("mv-kgf2", "08:30 PM", "Kannada", "2D", BigDecimal.valueOf(320))
                                ))
                        )),
                new Venue("v-ncpa", "NCPA", "mum", "Nariman Point, Mumbai",
                        List.of(
                                new Screen("Main Stage", List.of(
                                        new ShowTime("evt-arijit", "07:00 PM", "Hindi", "Live", BigDecimal.valueOf(1200)),
                                        new ShowTime("evt-theatre", "03:00 PM", "English", "Live", BigDecimal.valueOf(900))
                                ))
                        )),
                new Venue("v-comedy", "The Habitat", "mum", "Khar West, Mumbai",
                        List.of(
                                new Screen("Club Stage", List.of(
                                        new ShowTime("evt-standup", "08:00 PM", "English", "Club", BigDecimal.valueOf(600)),
                                        new ShowTime("evt-standup", "10:00 PM", "English", "Club", BigDecimal.valueOf(650))
                                ))
                        )),
                new Venue("v-del-mall", "PVR Pacific", "del", "Subhash Nagar, Delhi",
                        List.of(
                                new Screen("Audi 5", List.of(
                                        new ShowTime("mv-barbie", "12:00 PM", "English", "2D", BigDecimal.valueOf(340)),
                                        new ShowTime("mv-jawan", "03:30 PM", "Hindi", "2D", BigDecimal.valueOf(360))
                                )),
                                new Screen("Audi 6", List.of(
                                        new ShowTime("mv-jawan", "07:00 PM", "Hindi", "2D", BigDecimal.valueOf(380))
                                ))
                        )),
                new Venue("v-hyd-imax", "Prasads IMAX", "hyd", "Necklace Road, Hyderabad",
                        List.of(
                                new Screen("Large Screen", List.of(
                                        new ShowTime("mv-kgf2", "01:00 PM", "Telugu", "IMAX", BigDecimal.valueOf(420)),
                                        new ShowTime("mv-jawan", "06:00 PM", "Hindi", "IMAX", BigDecimal.valueOf(440))
                                )),
                                new Screen("Mini Screen", List.of(
                                        new ShowTime("evt-standup", "05:00 PM", "English", "Club", BigDecimal.valueOf(500))
                                ))
                        ))
        );
    }
}
