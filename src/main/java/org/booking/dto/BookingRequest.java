package org.booking.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    private Long showId;
    private List<String> seatNumbers;
}