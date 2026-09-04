package designpattern.usecase.bookmyshow.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Show {
    private int showId;
    private int movieId;
    private int theaterId;
    private LocalDate showDate;
    private LocalTime showTime;

    public Show(int showId, int movieId, int theaterId, LocalDate showDate, LocalTime showTime) {
        this.showId = showId;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.showDate = showDate;
        this.showTime = showTime;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getMovie() {
        return movieId;
    }

    public void setMovie(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "Show{" +
                "showId=" + showId +
                ", movieId=" + movieId +
                ", theaterId=" + theaterId +
                ", showDate=" + showDate +
                ", showTime=" + showTime +
                '}';
    }
}
