package org.app.booking.entity;

import jakarta.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "idx_show_seat", columnList = "show_id, seatNumber")
})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    private boolean booked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Show show;

    //  Added this for OPTIMISTIC LOCKING
    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}