package org.app.booking.pricing;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {

    @Test
    void calculatePrice_noDiscount() {

        PricingService service = new PricingService(List.of());

        double result = service.calculatePrice(2, LocalTime.of(18, 0));

        // 200 * 2 = 400
        assertEquals(400.0, result);
    }

    @Test
    void calculatePrice_afternoonDiscount_applied() {

        PricingService service =
                new PricingService(List.of(new AfternoonDiscount()));

        double result = service.calculatePrice(2, LocalTime.of(14, 0));

        // base = 200 * 2 = 400
        // 20% discount → 400 * 0.8 = 320
        assertEquals(320.0, result);
    }

    @Test
    void calculatePrice_afternoonDiscount_notApplied() {

        PricingService service =
                new PricingService(List.of(new AfternoonDiscount()));

        double result = service.calculatePrice(2, LocalTime.of(18, 0));

        // no discount
        assertEquals(400.0, result);
    }

    @Test
    void calculatePrice_thirdTicketDiscount_applied() {

        PricingService service =
                new PricingService(List.of(new ThirdTicketDiscount()));

        double result = service.calculatePrice(3, LocalTime.of(18, 0));

        // base = 600
        // minus 100
        assertEquals(500.0, result);
    }

    @Test
    void calculatePrice_thirdTicketDiscount_notApplied() {

        PricingService service =
                new PricingService(List.of(new ThirdTicketDiscount()));

        double result = service.calculatePrice(2, LocalTime.of(18, 0));

        // base = 400, no discount
        assertEquals(400.0, result);
    }

    @Test
    void calculatePrice_bothDiscounts_appliedInOrder() {

        PricingService service =
                new PricingService(List.of(
                        new AfternoonDiscount(),
                        new ThirdTicketDiscount()
                ));

        double result = service.calculatePrice(3, LocalTime.of(14, 0));

        // base = 600
        // afternoon → 600 * 0.8 = 480
        // third ticket → 480 - 100 = 380

        assertEquals(380.0, result);
    }
}