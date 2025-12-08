package com.abhinav.bookmyshow.model;

import java.util.List;

public record Venue(
        String id,
        String name,
        String city,
        String address,
        List<Screen> screens
) {
}
