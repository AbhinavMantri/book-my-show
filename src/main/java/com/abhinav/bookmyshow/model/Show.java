package com.abhinav.bookmyshow.model;

import java.time.LocalDateTime;

public class Show {
    private Long id;
    private Long eventId;
    private LocalDateTime time;
    private int capacity;
    private int booked;

    public Show(Long id, Long eventId, LocalDateTime time, int capacity) {
        this.id = id;
        this.eventId = eventId;
        this.time = time;
        this.capacity = capacity;
        this.booked = 0;
    }

    public synchronized boolean bookSeat() {
        if (booked < capacity) {
            booked++;
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getRemainingSeats() {
        return capacity - booked;
    }
}
