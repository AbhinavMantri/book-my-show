package com.abhinav.bookmyshow.service;

import com.abhinav.bookmyshow.model.City;
import com.abhinav.bookmyshow.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> listCities() {
        return cityRepository.findAll();
    }
}
