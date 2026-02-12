package org.app.booking.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void testMovieEntity() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Inception");
        movie.setLanguage("English");
        movie.setGenre("Sci-Fi");

        assertEquals(1L, movie.getId());
        assertEquals("Inception", movie.getName());
        assertEquals("English", movie.getLanguage());
        assertEquals("Sci-Fi", movie.getGenre());
    }

    @Test
    void testTheatreEntity() {
        Theatre theatre = new Theatre();
        theatre.setId(1L);
        theatre.setName("PVR");
        theatre.setCity("Delhi");

        assertEquals(1L, theatre.getId());
        assertEquals("PVR", theatre.getName());
        assertEquals("Delhi", theatre.getCity());
    }

    @Test
    void testShowEntity() {
        Movie movie = new Movie();
        Theatre theatre = new Theatre();

        Show show = new Show();
        show.setId(10L);
        show.setShowDate(LocalDate.now());
        show.setShowTime(LocalTime.NOON);
        show.setMovie(movie);
        show.setTheatre(theatre);

        assertEquals(10L, show.getId());
        assertNotNull(show.getShowDate());
        assertEquals(LocalTime.NOON, show.getShowTime());
        assertEquals(movie, show.getMovie());
        assertEquals(theatre, show.getTheatre());
    }

    @Test
    void testSeatEntity() {
        Show show = new Show();

        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        seat.setBooked(true);
        seat.setShow(show);

        assertEquals("A1", seat.getSeatNumber());
        assertTrue(seat.isBooked());
        assertEquals(show, seat.getShow());
    }

    @Test
    void testBookingEntity() {
        Show show = new Show();

        Booking booking = new Booking();
        booking.setId(5L);
        booking.setTotalAmount(500.0);
        booking.setBookedAt(LocalDateTime.now());
        booking.setShow(show);

        assertEquals(5L, booking.getId());
        assertEquals(500.0, booking.getTotalAmount());
        assertNotNull(booking.getBookedAt());
        assertEquals(show, booking.getShow());
    }
}