package com.abhinav.bookmyshow.model;

import java.util.List;

public record Screen(String name, List<ShowTime> shows) {
}
