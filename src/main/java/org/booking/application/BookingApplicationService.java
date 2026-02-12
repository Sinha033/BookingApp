package org.booking.application;



import org.booking.entity.*;
import org.booking.repository.*;
import org.booking.exception.SeatAlreadyBookedException;
import org.booking.pricing.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingApplicationService {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final PricingService pricingService;

    @Transactional
    public String book(Long showId, List<String> seatNumbers) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<Seat> seats =
                seatRepository.findByShowIdAndSeatNumberIn(showId, seatNumbers);

        for (Seat seat : seats) {
            if (seat.isBooked()) {
                throw new SeatAlreadyBookedException(
                        "Seat already booked: " + seat.getSeatNumber());
            }
        }

        seats.forEach(seat -> seat.setBooked(true));
        seatRepository.saveAll(seats);

        double total = pricingService.calculate(
                seats.size(),
                show.getShowTime());

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setBookedAt(LocalDateTime.now());
        booking.setTotalAmount(total);

        bookingRepository.save(booking);

        return "Booking Successful. Total Amount: " + total;
    }
}