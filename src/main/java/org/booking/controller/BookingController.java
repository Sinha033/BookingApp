package org.booking.controller;

import org.booking.dto.BookingRequest;
import org.booking.entity.Show;
import org.booking.repository.ShowRepository;
import org.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {

    private final ShowRepository showRepository;
    private final BookingService bookingService;

    @GetMapping("/browse")
    public List<Show> browse(
            @RequestParam String city,
            @RequestParam String movie,
            @RequestParam String date) {

        return showRepository.findByTheatreCityAndMovieNameAndShowDate(
                city,
                movie,
                LocalDate.parse(date)
        );
    }

    @PostMapping("/book")
    public String book(@RequestBody BookingRequest request) {
        return bookingService.bookTickets(request.getShowId(), request.getSeatNumbers());
    }
}