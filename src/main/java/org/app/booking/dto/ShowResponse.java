package org.app.booking.dto;

import org.app.booking.entity.Show;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowResponse {

    private Long showId;
    private String movieName;
    private String theatreName;
    private String city;
    private LocalDate showDate;
    private LocalTime showTime;

    public ShowResponse(Show show) {
        this.showId = show.getId();
        this.movieName = show.getMovie().getName();
        this.theatreName = show.getTheatre().getName();
        this.city = show.getTheatre().getCity();
        this.showDate = show.getShowDate();
        this.showTime = show.getShowTime();
    }

    public Long getShowId() {
        return showId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }
}
