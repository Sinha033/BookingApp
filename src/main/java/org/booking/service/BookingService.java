package org.booking.service;

import org.booking.entity.*;
import org.booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final PricingService pricingService;

    @Transactional
    public String bookTickets(Long showId, List<String> seatNumbers) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<Seat> seats = seatRepository.findByShowIdAndSeatNumberIn(showId, seatNumbers);

        for (Seat seat : seats) {
            if (seat.isBooked()) {
                throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());
            }
        }

        seats.forEach(seat -> seat.setBooked(true));
        seatRepository.saveAll(seats);

        double totalAmount = pricingService.calculatePrice(seats.size(), show.getShowTime());

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setTotalAmount(totalAmount);
        bookingRepository.save(booking);

        return "Booking Successful. Total: " + totalAmount;
    }
}