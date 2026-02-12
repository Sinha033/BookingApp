package org.booking.pricing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final List<DiscountStrategy> strategies;

    public double calculate(int tickets, LocalTime showTime) {

        double basePrice = 200;
        double total = basePrice * tickets;

        for (DiscountStrategy strategy : strategies) {
            total = strategy.apply(total, tickets, showTime);
        }

        return total;
    }
}