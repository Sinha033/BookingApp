package org.booking.service;

import org.springframework.stereotype.Service;
import java.time.LocalTime;

@Service
public class PricingService {

    public double calculatePrice(int ticketCount, LocalTime showTime) {

        double basePrice = 200;
        double total = ticketCount * basePrice;

        if (ticketCount >= 3) {
            total -= basePrice * 0.5;
        }

        if (showTime.isBefore(LocalTime.of(17, 0))) {
            total *= 0.8;
        }

        return total;
    }
}