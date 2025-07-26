package com.abhinav.bookmyshow.model;

public class Ticket {
    private Long id;
    private Long showId;

    public Ticket(Long id, Long showId) {
        this.id = id;
        this.showId = showId;
    }

    public Long getId() {
        return id;
    }

    public Long getShowId() {
        return showId;
    }
}
