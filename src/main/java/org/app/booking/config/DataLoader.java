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

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final MovieRepository movieRepo;
    private final TheatreRepository theatreRepo;
    private final ShowRepository showRepo;
    private final SeatRepository seatRepo;

    @PostConstruct
    public void loadData() {

        Movie movie = new Movie();
        movie.setName("Jawan");
        movie.setLanguage("Hindi");
        movie.setGenre("Action");
        movieRepo.save(movie);

        Theatre theatre = new Theatre();
        theatre.setName("PVR");
        theatre.setCity("Delhi");
        theatreRepo.save(theatre);

        Show show = new Show();
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowDate(LocalDate.now());
        show.setShowTime(LocalTime.of(14, 0));
        showRepo.save(show);

        for (int i = 1; i <= 10; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("A" + i);
            seat.setBooked(false);
            seat.setShow(show);
            seatRepo.save(seat);
        }
    }
}