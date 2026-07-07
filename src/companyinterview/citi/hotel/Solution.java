package companyinterview.citi.hotel;

/*
We are developing a hotel reservation system that tracks bookings, rooms, and guest information.

The program includes three classes: `Room`, `Reservation`, and `HotelManager`.

Classes:
* The `Room` class represents a hotel room with its properties.
* The `Reservation` class holds information about a guest's booking.
* The `HotelManager` class manages all reservations and provides analytical methods.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for HotelManager is not passing due to a bug in the code. Make the necessary changes to HotelManager to fix the bug.
*/

/*
2) We want to add a new function called "getOccupancyRate" to the HotelManager class.
This function calculates the occupancy rate for a specific date by determining what percentage
of rooms are booked on that date.

For example, if there are 10 total rooms and 7 are booked on "2024-02-15":
The function should return 70.0

The function takes a date string (YYYY-MM-DD) and returns the occupancy rate as a percentage.
A room is considered occupied on a date if that date falls between checkIn (inclusive) and 
checkOut (exclusive).

To assist you in testing this new function, we have provided the testGetOccupancyRate function.
*/

import java.time.LocalDate;
import java.util.*;
import org.junit.*;

class Room {
    /** Data about a hotel room. */
    String roomNumber;
    String roomType;        // "Single", "Double", "Suite", "Deluxe"
    double pricePerNight;
    int maxOccupancy;

    Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.maxOccupancy = maxOccupancy;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Room room = (Room) other;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}

class Reservation {
    /** Data about a hotel reservation. */
    Room room;
    String guestName;
    String checkIn;         // Format: "YYYY-MM-DD"
    String checkOut;        // Format: "YYYY-MM-DD"
    int numberOfGuests;

    Reservation(Room room, String guestName, String checkIn, String checkOut, int numberOfGuests) {
        this.room = room;
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
    }

    int getNights() {
        /** Calculate number of nights (simplified - assumes same month). */
        String[] checkInParts = checkIn.split("-");
        String[] checkOutParts = checkOut.split("-");
        int checkInDay = Integer.parseInt(checkInParts[2]);
        int checkOutDay = Integer.parseInt(checkOutParts[2]);
        return checkOutDay - checkInDay;
    }

    double getTotalCost() {
        return getNights() * room.pricePerNight;
    }
}

class HotelManager {
    /**
     * Manages all hotel reservations and provides analytical methods.
     */
    ArrayList<Reservation> reservations = new ArrayList<>();
    ArrayList<Room> allRooms = new ArrayList<>();
    String hotelName;

    HotelManager(String hotelName, ArrayList<Room> rooms) {
        this.hotelName = hotelName;
        this.allRooms = rooms;
    }

    void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    int getTotalReservations() {
        return reservations.size();
    }

    double getTotalRevenue() {
        return reservations.stream().mapToDouble(r -> r.getTotalCost()).sum();
    }

    double getAverageReservationValue() {
        /** 
         * Returns the average revenue per reservation.
         * BUG: This method has a bug - fix it!
         */
        return getTotalRevenue() / getTotalReservations();
    }

    int getReservationsByRoomType(String roomType) {
        /** Returns count of reservations for a specific room type. */
        return (int) reservations.stream()
            .filter(r -> r.room.roomType.equals(roomType))
            .count();
    }

    public double getOccupancyRate(String date) {
        // Write your code here to solve this problem
        // A room is occupied on a date if: checkIn <= date < checkOut

        int totalRoom = allRooms.size();

        if(totalRoom == 0) {
            return 0.0;
        }
        LocalDate givenDate = LocalDate.parse(date);

        long value = reservations.stream().filter(i -> !LocalDate.parse(i.checkIn).isAfter(givenDate)
                && LocalDate.parse(i.checkOut).isAfter(givenDate)).count();

        return (double) value/totalRoom * 100 ;
    }
}

public class Solution {
    public static void main(String[] args) {
        testReservation();
        testHotelManager();
        testGetOccupancyRate();
    }

    public static void testReservation() {
        System.out.println("Running testReservation");
        Room room = new Room("101", "Double", 150.0, 2);
        Reservation reservation = new Reservation(room, "John Doe", "2024-02-01", "2024-02-05", 2);

        Assert.assertEquals(4, reservation.getNights());
        Assert.assertEquals(600.0, reservation.getTotalCost(), 0.01);
    }

    public static void testHotelManager() {
        System.out.println("Running testHotelManager");
        
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("101", "Single", 100.0, 1));
        rooms.add(new Room("102", "Double", 150.0, 2));
        rooms.add(new Room("103", "Suite", 300.0, 4));

        HotelManager manager = new HotelManager("Grand Hotel", rooms);

        Assert.assertEquals(0, manager.getTotalReservations());
        Assert.assertEquals(0.0, manager.getTotalRevenue(), 0.01);

        Reservation r1 = new Reservation(rooms.get(0), "Alice", "2024-02-01", "2024-02-03", 1);
        Reservation r2 = new Reservation(rooms.get(1), "Bob", "2024-02-01", "2024-02-05", 2);
        Reservation r3 = new Reservation(rooms.get(2), "Charlie", "2024-02-10", "2024-02-12", 3);

        manager.addReservation(r1);
        manager.addReservation(r2);
        manager.addReservation(r3);

        Assert.assertEquals(3, manager.getTotalReservations());
        Assert.assertEquals(1400.0, manager.getTotalRevenue(), 0.01);
        Assert.assertEquals(466.67, manager.getAverageReservationValue(), 0.01);
        Assert.assertEquals(1, manager.getReservationsByRoomType("Suite"));
    }

    public static void testGetOccupancyRate() {
        System.out.println("Running testGetOccupancyRate");
        
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room("10" + i, "Double", 150.0, 2));
        }

        HotelManager manager = new HotelManager("Grand Hotel", rooms);

        // Add 7 reservations for Feb 15
        for (int i = 0; i < 7; i++) {
            Reservation r = new Reservation(rooms.get(i), "Guest" + i, "2024-02-14", "2024-02-16", 2);
            manager.addReservation(r);
        }

        Assert.assertEquals(70.0, manager.getOccupancyRate("2024-02-15"), 0.01);
        Assert.assertEquals(0.0, manager.getOccupancyRate("2024-02-20"), 0.01);
    }
}
