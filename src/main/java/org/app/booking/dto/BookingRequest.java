package org.app.booking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    private Long showId;
    private List<String> seatNumbers;
}