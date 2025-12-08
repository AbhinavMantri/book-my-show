# Book My Show - Ticket Discovery API

Simple Spring Boot service that models BookMyShow-style discovery for movies and live events. It seeds in-memory data for multiple cities, shows available events, and lists theatres/screens/shows for a chosen movie.

## Running locally

```bash
./mvnw spring-boot:run
```

## REST endpoints

- `GET /api/cities` — list supported cities.
- `GET /api/events?city={cityCode}&type={MOVIE|EVENT}&q={query}` — list events for a city with optional type filter and search.
- `GET /api/movies?city={cityCode}&q={query}` — quick movie search for a city.
- `GET /api/movies/{movieId}/venues?city={cityCode}` — show venues/screens/timings for a movie within a city.

City codes currently available: `blr`, `mum`, `del`, `hyd`.
