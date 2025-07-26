package com.abhinav.bookmyshow.model;

public class Event {
    private Long id;
    private String name;
    private Long cityId;

    public Event(Long id, String name, Long cityId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCityId() {
        return cityId;
    }
}
