package com.abhinav.bookmyshow.service;

import com.abhinav.bookmyshow.model.Show;
import com.abhinav.bookmyshow.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public List<Show> getShowsByEvent(Long eventId) {
        return showRepository.findAllByEvent(eventId);
    }

    public Show getShow(Long id) {
        return showRepository.findById(id);
    }
}
