package org.app.booking.dto;

import org.app.booking.entity.Movie;
import org.app.booking.entity.Show;
import org.app.booking.entity.Theatre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ShowResponseTest {

    @Test
    void testShowResponseConstructor() {

        Movie movie = new Movie();
        movie.setName("Avatar");

        Theatre theatre = new Theatre();
        theatre.setName("INOX");
        theatre.setCity("Mumbai");

        Show show = new Show();
        show.setId(100L);
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowDate(LocalDate.of(2025, 1, 1));
        show.setShowTime(LocalTime.of(14, 0));

        ShowResponse response = new ShowResponse(show);

        assertEquals(100L, response.getShowId());
        assertEquals("Avatar", response.getMovieName());
        assertEquals("INOX", response.getTheatreName());
        assertEquals("Mumbai", response.getCity());
        assertEquals(LocalDate.of(2025, 1, 1), response.getShowDate());
        assertEquals(LocalTime.of(14, 0), response.getShowTime());
    }
}