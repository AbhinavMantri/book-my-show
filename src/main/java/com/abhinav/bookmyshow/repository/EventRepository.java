package com.abhinav.bookmyshow.repository;

import com.abhinav.bookmyshow.model.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EventRepository {
    private final Map<Long, Event> events = new HashMap<>();

    @PostConstruct
    public void init() {
        events.put(1L, new Event(1L, "Rock Concert", 1L));
        events.put(2L, new Event(2L, "Movie Premiere", 1L));
        events.put(3L, new Event(3L, "Comedy Show", 2L));
    }

    public List<Event> findAllByCity(Long cityId) {
        return events.values().stream()
                .filter(e -> e.getCityId().equals(cityId))
                .collect(Collectors.toList());
    }

    public Event findById(Long id) {
        return events.get(id);
    }
}
