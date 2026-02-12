package org.app.booking.pricing;

import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class AfternoonDiscount implements DiscountStrategy {

    @Override
    public double apply(double total, int ticketCount, LocalTime showTime) {

        if (showTime.isBefore(LocalTime.of(17, 0))) {
            return total * 0.8;
        }
        return total;
    }
}