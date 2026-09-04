package designpattern.usecase.bookmyshow.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Screen {
    private int screenId;
    private List<Seat> seatList;
    private Map<LocalDate, Show> movieMap;

    public Screen() {
    }

    public Screen(int screenId, List<Seat> seatList) {
        this.screenId = screenId;
        this.seatList = seatList;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public Map<LocalDate, Show> getMovieMap() {
        return movieMap;
    }

    public void setMovieMap(Map<LocalDate, Show> movieMap) {
        this.movieMap = movieMap;
    }

    @Override
    public String toString() {
        return "Screen{" +
                "screenId=" + screenId +
                ", seatList=" + seatList +
                '}';
    }
}
