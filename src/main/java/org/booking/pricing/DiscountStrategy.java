package org.booking.pricing;

import java.time.LocalTime;

public interface DiscountStrategy {

    double apply(double total, int ticketCount, LocalTime showTime);
}