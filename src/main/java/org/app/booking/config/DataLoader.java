package org.app.booking.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.app.booking.entity.Movie;
import org.app.booking.entity.Seat;
import org.app.booking.entity.Show;
import org.app.booking.entity.Theatre;
import org.app.booking.repository.MovieRepository;
import org.app.booking.repository.SeatRepository;
import org.app.booking.repository.ShowRepository;
import org.app.booking.repository.TheatreRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final MovieRepository movieRepo;
    private final TheatreRepository theatreRepo;
    private final ShowRepository showRepo;
    private final SeatRepository seatRepo;

    @PostConstruct
    public void loadData() {

        // Prevent duplicate inserts on restart
        if (movieRepo.count() > 0) {
            return;
        }

        List<Movie> movies = new ArrayList<>();
        List<Theatre> theatres = new ArrayList<>();
        List<Show> shows = new ArrayList<>();

        //  Create 10 Movies
        for (int i = 1; i <= 10; i++) {
            Movie movie = new Movie();
            movie.setName("Movie-" + i);
            movie.setLanguage(i % 2 == 0 ? "Hindi" : "English");
            movie.setGenre(i % 2 == 0 ? "Action" : "Drama");
            movies.add(movie);
        }
        movieRepo.saveAll(movies);

        //  Create 10 Theatres
        for (int i = 1; i <= 10; i++) {
            Theatre theatre = new Theatre();
            theatre.setName("Theatre-" + i);
            theatre.setCity(i % 2 == 0 ? "Delhi" : "Mumbai");
            theatres.add(theatre);
        }
        theatreRepo.saveAll(theatres);

        //  Create 10 Shows (link movie + theatre)
        for (int i = 0; i < 10; i++) {
            Show show = new Show();
            show.setMovie(movies.get(i));
            show.setTheatre(theatres.get(i));
            show.setShowDate(LocalDate.now().plusDays(i));
            show.setShowTime(LocalTime.of(10 + i, 0));
            shows.add(show);
        }
        showRepo.saveAll(shows);

        //  Create 10 Seats per Show (Total 100 seats)
        for (Show show : shows) {
            for (int i = 1; i <= 10; i++) {
                Seat seat = new Seat();
                seat.setSeatNumber("A" + i);
                seat.setBooked(false);
                seat.setShow(show);
                seatRepo.save(seat);
            }
        }

//        System.out.println(" Database Loaded with 10 Movies, 10 Theatres, 10 Shows, 100 Seats");
    }
}