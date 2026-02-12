package org.app.booking.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void handleSeatAlreadyBookedException() {

        SeatAlreadyBookedException ex =
                new SeatAlreadyBookedException("Seat already booked: A1");

        ResponseEntity<ErrorResponse> response =
                handler.handleSeatError(ex);

        assertEquals(409, response.getStatusCode().value());
        assertEquals("Seat already booked: A1", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void handleOptimisticLockException() {

        ResponseEntity<ErrorResponse> response =
                handler.handleOptimisticLock();

        assertEquals(409, response.getStatusCode().value());
        assertEquals("Seat already booked by another user.",
                response.getBody().getMessage());
    }
}