package designpattern.usecase.bookmyshow.controller;

import designpattern.usecase.bookmyshow.entity.Movie;
import designpattern.usecase.bookmyshow.entity.Theater;
import designpattern.usecase.bookmyshow.services.TheaterService;

import java.util.Date;

public class TheaterController {

    TheaterService theaterService = new TheaterService();

    public void addTheater(Theater theater) {
        theaterService.addTheater(theater, theater.getCity());
    }

    public void getShow(Theater theater, Movie movie, Date date) {
        return;
    }
    public void getMovie(String city , Date date) {
        return;
    }
}
