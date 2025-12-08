package com.abhinav.bookmyshow.model;

import java.math.BigDecimal;

public record ShowTime(
        String eventId,
        String startTime,
        String language,
        String format,
        BigDecimal price
) {
}
