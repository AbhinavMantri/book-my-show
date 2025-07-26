package com.abhinav.bookmyshow.repository;

import com.abhinav.bookmyshow.model.City;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CityRepository {
    private final Map<Long, City> cities = new HashMap<>();

    @PostConstruct
    public void init() {
        cities.put(1L, new City(1L, "Delhi"));
        cities.put(2L, new City(2L, "Mumbai"));
    }

    public List<City> findAll() {
        return new ArrayList<>(cities.values());
    }

    public City findById(Long id) {
        return cities.get(id);
    }
}
