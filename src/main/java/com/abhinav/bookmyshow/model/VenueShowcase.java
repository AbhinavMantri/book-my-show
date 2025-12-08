package com.abhinav.bookmyshow.model;

import java.util.List;

public record VenueShowcase(
        String venueId,
        String venueName,
        String address,
        List<Screen> screens
) {
}
