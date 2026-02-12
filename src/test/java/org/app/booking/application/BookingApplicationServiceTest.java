package org.app.booking.application;

import org.app.booking.dto.BookingRequest;
import org.app.booking.entity.Seat;
import org.app.booking.entity.Show;
import org.app.booking.exception.SeatAlreadyBookedException;
import org.app.booking.pricing.PricingService;
import org.app.booking.repository.BookingRepository;
import org.app.booking.repository.SeatRepository;
import org.app.booking.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingApplicationServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PricingService pricingService;

    @InjectMocks
    private BookingApplicationService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookTickets_success() {

        BookingRequest request = new BookingRequest();
        request.setShowId(1L);
        request.setSeatNumbers(List.of("A1", "A2"));

        Show show = new Show();
        show.setId(1L);
        show.setShowTime(LocalTime.of(14, 0));

        Seat seat1 = new Seat();
        seat1.setSeatNumber("A1");
        seat1.setBooked(false);

        Seat seat2 = new Seat();
        seat2.setSeatNumber("A2");
        seat2.setBooked(false);

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(seatRepository.findByShowIdAndSeatNumberIn(1L, List.of("A1", "A2")))
                .thenReturn(List.of(seat1, seat2));
        when(pricingService.calculatePrice(2, show.getShowTime()))
                .thenReturn(400.0);

        String result = service.bookTickets(request);

        assertEquals("Booking Successful. Total Amount: 400.0", result);

        verify(seatRepository).saveAll(any());
        verify(bookingRepository).save(any());
    }

    @Test
    void bookTickets_showNotFound() {

        BookingRequest request = new BookingRequest();
        request.setShowId(1L);

        when(showRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.bookTickets(request));
    }

    @Test
    void bookTickets_invalidSeatSelection() {

        BookingRequest request = new BookingRequest();
        request.setShowId(1L);
        request.setSeatNumbers(List.of("A1", "A2"));

        Show show = new Show();
        show.setId(1L);

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(seatRepository.findByShowIdAndSeatNumberIn(1L, List.of("A1","A2")))
                .thenReturn(List.of()); // mismatch

        assertThrows(RuntimeException.class,
                () -> service.bookTickets(request));
    }

    @Test
    void bookTickets_seatAlreadyBooked() {

        BookingRequest request = new BookingRequest();
        request.setShowId(1L);
        request.setSeatNumbers(List.of("A1"));

        Show show = new Show();
        show.setId(1L);

        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        seat.setBooked(true);

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(seatRepository.findByShowIdAndSeatNumberIn(1L, List.of("A1")))
                .thenReturn(List.of(seat));

        assertThrows(SeatAlreadyBookedException.class,
                () -> service.bookTickets(request));
    }

    @Test
    void bookTickets_optimisticLockingFailure() {

        BookingRequest request = new BookingRequest();
        request.setShowId(1L);

        when(showRepository.findById(1L))
                .thenThrow(new OptimisticLockingFailureException("error"));

        assertThrows(SeatAlreadyBookedException.class,
                () -> service.bookTickets(request));
    }
}