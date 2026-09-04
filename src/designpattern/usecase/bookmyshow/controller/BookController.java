package designpattern.usecase.bookmyshow.controller;

import designpattern.usecase.bookmyshow.services.BookService;
import designpattern.usecase.bookmyshow.services.TheaterService;

public class BookController {

    BookService bookService = new BookService();

    public boolean bookingTicket() {
        return false;
    }
}
