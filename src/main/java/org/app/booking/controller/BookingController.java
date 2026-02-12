package org.app.booking.controller;

import org.app.booking.application.BookingApplicationService;
import org.app.booking.dto.BookingRequest;
import org.app.booking.dto.ShowResponse;
import org.app.booking.entity.Show;
import org.app.booking.repository.ShowRepository;
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
    public List<ShowResponse> browse(
            @RequestParam String city,
            @RequestParam String movie,
            @RequestParam String date) {
        List<Show> shows = showRepository
                .findByTheatreCityAndMovieNameAndShowDate(
                        city, movie, LocalDate.parse(date));

        return shows.stream()
                .map(ShowResponse::new)
                .toList();
    }

    @PostMapping("/book")
    public String book(@RequestBody BookingRequest request) {
        return service.bookTickets(request);
    }

    @GetMapping("/browse-all")
    public List<ShowResponse> browseAll() {
        return showRepository.findAll()
                .stream()
                .map(ShowResponse::new)
                .toList();
    }
}