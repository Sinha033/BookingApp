package org.app.booking.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatAlreadyBookedExceptionTest {

    @Test
    void constructor_shouldSetMessage() {

        SeatAlreadyBookedException ex =
                new SeatAlreadyBookedException("Seat already booked");

        assertEquals("Seat already booked", ex.getMessage());
    }
}