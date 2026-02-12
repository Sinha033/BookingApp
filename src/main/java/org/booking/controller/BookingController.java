package org.booking.controller;

import org.booking.application.BookingApplicationService;
import org.booking.entity.Show;
import org.booking.repository.ShowRepository;
import org.booking.dto.BookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {

    private final BookingApplicationService service;
    private final ShowRepository showRepository;

    @GetMapping("/browse")
    public List<Show> browse(
            @RequestParam String city,
            @RequestParam String movie,
            @RequestParam String date) {

        return showRepository.findByTheatreCityAndMovieNameAndShowDate(
                city,
                movie,
                LocalDate.parse(date));
    }

    @PostMapping("/book")
    public String book(@RequestBody BookingRequest request) {
        return service.book(request.getShowId(), request.getSeatNumbers());
    }
}