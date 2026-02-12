package org.app.booking.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestTest {

    @Test
    void gettersAndSetters_shouldWork() {

        BookingRequest request = new BookingRequest();

        request.setShowId(1L);
        request.setSeatNumbers(List.of("A1", "A2"));

        assertEquals(1L, request.getShowId());
        assertEquals(2, request.getSeatNumbers().size());
        assertEquals("A1", request.getSeatNumbers().get(0));
    }
}