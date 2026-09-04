package designpattern.usecase.bookmyshow.entity;

import java.util.List;

public class Theater {
    private int theaterId;
    private String theaterName;
    private String city;
    private List<Screen> screenList;

    public Theater(int theaterId, String theaterName, String city, List<Screen> screenList) {
        this.theaterId = theaterId;
        this.city = city;
        this.screenList = screenList;
        this.theaterName = theaterName;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Screen> getScreenList() {
        return screenList;
    }

    public void setScreenList(List<Screen> screenList) {
        this.screenList = screenList;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "theaterId=" + theaterId +
                ", city='" + city + '\'' +
                ", screenList=" + screenList +
                '}';
    }
}
