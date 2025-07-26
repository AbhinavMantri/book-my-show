package com.abhinav.bookmyshow.service;

import com.abhinav.bookmyshow.model.Event;
import com.abhinav.bookmyshow.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEventsByCity(Long cityId) {
        return eventRepository.findAllByCity(cityId);
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id);
    }
}
