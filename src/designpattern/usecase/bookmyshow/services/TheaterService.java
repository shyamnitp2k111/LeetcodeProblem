package designpattern.usecase.bookmyshow.services;

import designpattern.usecase.bookmyshow.entity.Theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TheaterService {

    Map<String, List<Theater>> theaterMap;

    public Map<String, List<Theater>> getTheaterMap() {
        return theaterMap;
    }

    public void setTheaterMap(Map<String, List<Theater>> theaterMap) {
        this.theaterMap = theaterMap;
    }

    public void addTheater(Theater theater, String city ) {
        theaterMap.computeIfAbsent(city, k -> new ArrayList<>()).add(theater);
    }
}
