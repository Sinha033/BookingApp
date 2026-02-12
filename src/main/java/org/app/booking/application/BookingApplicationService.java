package org.app.booking.application;


import org.app.booking.dto.BookingRequest;
import org.app.booking.entity.Booking;
import org.app.booking.entity.Seat;
import org.app.booking.entity.Show;
import org.app.booking.exception.SeatAlreadyBookedException;
import org.app.booking.pricing.PricingService;
import org.app.booking.repository.BookingRepository;
import org.app.booking.repository.SeatRepository;
import org.app.booking.repository.ShowRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingApplicationService {

    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;
    private final PricingService pricingService;

    public BookingApplicationService(SeatRepository seatRepository,
                                     ShowRepository showRepository,
                                     BookingRepository bookingRepository,
                                     PricingService pricingService) {
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
        this.pricingService = pricingService;
    }

    @Transactional
    public String bookTickets(BookingRequest request) {

        try {
            Show show = showRepository.findById(request.getShowId())
                    .orElseThrow(() -> new RuntimeException("Show not found"));

            List<Seat> seats = seatRepository
                    .findByShowIdAndSeatNumberIn(show.getId(), request.getSeatNumbers());

            if (seats.size() != request.getSeatNumbers().size()) {
                throw new RuntimeException("Invalid seat selection");
            }

            for (Seat seat : seats) {
                if (seat.isBooked()) {
                    throw new SeatAlreadyBookedException(
                            "Seat already booked: " + seat.getSeatNumber()
                    );
                }
            }

            // marking as booked
            seats.forEach(seat -> seat.setBooked(true));
            seatRepository.saveAll(seats);

            double totalAmount = pricingService.calculatePrice(
                    seats.size(),
                    show.getShowTime()
            );

            Booking booking = new Booking();
            booking.setShow(show);
            booking.setTotalAmount(totalAmount);

            bookingRepository.save(booking);

            return "Booking Successful. Total Amount: " + totalAmount;

        } catch (OptimisticLockingFailureException ex) {
            throw new SeatAlreadyBookedException(
                    "Seat already booked by another user."
            );
        }
    }
}