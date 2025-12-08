package com.abhinav.bookmyshow.model;

import java.util.List;

public record Event(
        String id,
        String title,
        EventType type,
        String language,
        String genre,
        int durationMinutes,
        List<String> tags,
        String description
) {
}
