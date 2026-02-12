package org.app.booking.repository;

import org.app.booking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByTheatreCityAndMovieNameAndShowDate(
            String city,
            String movieName,
            LocalDate showDate
    );
}
