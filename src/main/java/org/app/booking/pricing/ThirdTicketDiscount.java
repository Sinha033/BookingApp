package org.app.booking.pricing;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ThirdTicketDiscount implements DiscountStrategy {

    @Override
    public double apply(double total, int ticketCount, LocalTime showTime) {

        if (ticketCount >= 3) {
            return total - 100; // 50% of base ticket (200)
        }
        return total;
    }
}