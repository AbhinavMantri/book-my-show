package com.abhinav.bookmyshow.repository;

import com.abhinav.bookmyshow.model.Show;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ShowRepository {
    private final Map<Long, Show> shows = new HashMap<>();

    @PostConstruct
    public void init() {
        shows.put(1L, new Show(1L, 1L, LocalDateTime.now().plusHours(1), 2));
        shows.put(2L, new Show(2L, 1L, LocalDateTime.now().plusHours(3), 2));
        shows.put(3L, new Show(3L, 2L, LocalDateTime.now().plusHours(2), 2));
    }

    public List<Show> findAllByEvent(Long eventId) {
        return shows.values().stream()
                .filter(s -> s.getEventId().equals(eventId))
                .collect(Collectors.toList());
    }

    public Show findById(Long id) {
        return shows.get(id);
    }
}
