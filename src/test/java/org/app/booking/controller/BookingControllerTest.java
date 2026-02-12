package org.app.booking.controller;

import org.app.booking.application.BookingApplicationService;
import org.app.booking.entity.Movie;
import org.app.booking.entity.Show;
import org.app.booking.entity.Theatre;
import org.app.booking.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingApplicationService service;

    @MockBean
    private ShowRepository showRepository;

    // -------------------------------
    // BOOK SUCCESS TEST
    // -------------------------------
    @Test
    void book_success() throws Exception {

        when(service.bookTickets(any()))
                .thenReturn("Booking Successful. Total Amount: 400.0");

        mockMvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "showId": 1,
                                  "seatNumbers": ["A1","A2"]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking Successful. Total Amount: 400.0"));
    }

    // -------------------------------
    // BROWSE SUCCESS TEST
    // -------------------------------
    @Test
    void browse_success() throws Exception {

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Inception");

        Theatre theatre = new Theatre();
        theatre.setId(1L);
        theatre.setName("PVR");
        theatre.setCity("Delhi");

        Show show = new Show();
        show.setId(1L);
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowDate(LocalDate.now());

        when(showRepository
                .findByTheatreCityAndMovieNameAndShowDate(
                        any(), any(), any()))
                .thenReturn(List.of(show));

        mockMvc.perform(get("/api/browse")
                        .param("city", "Delhi")
                        .param("movie", "Inception")
                        .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk());
    }
}