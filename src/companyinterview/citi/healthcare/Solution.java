package companyinterview.citi.healthcare;

/*
We are developing a healthcare appointment management system that tracks patients, appointments, and doctor schedules.

The program includes three classes: `Patient`, `Appointment`, and `ClinicScheduler`.

Classes:
* The `Patient` class represents a patient in the healthcare system.
* The `Appointment` class holds information about a scheduled appointment.
* The `ClinicScheduler` class manages all appointments and provides scheduling analytics.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for ClinicScheduler is not passing due to a bug in the code. Make the necessary changes to ClinicScheduler to fix the bug.
*/

/*
2) We want to add a new function called "findBusiestDoctor" to the ClinicScheduler class.
This function analyzes all appointments and returns the doctor who has the most appointments
scheduled, along with the count.

For example, if appointments are:
- Dr. Smith: 5 appointments
- Dr. Johnson: 8 appointments
- Dr. Williams: 3 appointments

The function should return ["Dr. Johnson", 8].

If there's a tie, return the doctor whose name comes first alphabetically.

To assist you in testing this new function, we have provided the testFindBusiestDoctor function.
*/

import java.util.*;
import java.util.stream.Collectors;

import org.junit.*;

class Patient {
    /** Data about a patient. */
    String patientId;
    String name;
    int age;
    String bloodType;

    Patient(String patientId, String name, int age, String bloodType) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.bloodType = bloodType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Patient patient = (Patient) other;
        return patientId.equals(patient.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId);
    }
}

class Appointment {
    /** Data about a scheduled appointment. */
    Patient patient;
    String doctorName;
    String appointmentDate;     // Format: "YYYY-MM-DD"
    String appointmentTime;     // Format: "HH:MM"
    String appointmentType;     // "Checkup", "Follow-up", "Emergency", "Consultation"
    int durationMinutes;

    Appointment(Patient patient, String doctorName, String appointmentDate,
                String appointmentTime, String appointmentType, int durationMinutes) {
        this.patient = patient;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentType = appointmentType;
        this.durationMinutes = durationMinutes;
    }
}

class ClinicScheduler {
    /**
     * Manages appointments and provides scheduling analytics.
     */
    ArrayList<Appointment> appointments = new ArrayList<>();
    String clinicName;

    ClinicScheduler(String clinicName) {
        this.clinicName = clinicName;
    }

    void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    int getTotalAppointments() {
        return appointments.size();
    }

    int getAppointmentsByType(String type) {
        /** Returns count of appointments of a specific type. */
        return (int) appointments.stream()
                .filter(a -> a.appointmentType.equals(type))
                .count();
    }

    double getAverageDuration() {
        /**
         * Returns the average appointment duration in minutes.
         * BUG: This method has a bug - fix it!
         */


        System.out.printf("Total Appointment "+ getTotalAppointments());

        if(getTotalAppointments() == 0) {
            return 0.0;
        }
        int total = appointments.stream().mapToInt(a -> a.durationMinutes).sum();
        return (double) total / getTotalAppointments();
    }

    int getAppointmentsForDoctor(String doctorName) {
        /** Returns count of appointments for a specific doctor. */
        return (int) appointments.stream()
                .filter(a -> a.doctorName.equals(doctorName))
                .count();
    }

    int getAppointmentsOnDate(String date) {
        /** Returns count of appointments on a specific date. */
        return (int) appointments.stream()
                .filter(a -> a.appointmentDate.equals(date))
                .count();
    }

    public Object[] findBusiestDoctor() {

        if(appointments == null || appointments.isEmpty()){
            return   null;
        }
        // Write your code here to solve this problem

        Object[] answer = new Object[2];
        Optional<Map.Entry<String, Long>> entry = appointments.stream()
                .collect(Collectors.groupingBy(app -> app.doctorName, TreeMap::new, Collectors.counting()))
                .entrySet().stream().
                max( (e1,e2) -> {
                    if(e1.getValue() == e2.getValue()) {
                        return e2.getKey().compareTo(e1.getKey());
                    } else {
                        return (int) (e1.getValue() - e2.getValue());
                    }
                });
        System.out.println(entry);

        if(entry.isPresent()) {
            answer[0] = entry.get().getKey();
            answer[1] = entry.get().getValue().intValue();
        }
        return answer;
    }
}

public class Solution {
    public static void main(String[] args) {
        testAppointment();
        testClinicScheduler();
        testFindBusiestDoctor();
    }

    public static void testAppointment() {
        System.out.println("Running testAppointment");
        Patient patient = new Patient("P001", "John Doe", 35, "A+");
        Appointment appointment = new Appointment(patient, "Dr. Smith", "2024-02-15",
                "10:00", "Checkup", 30);

        Assert.assertEquals(patient, appointment.patient);
        Assert.assertEquals("Dr. Smith", appointment.doctorName);
        Assert.assertEquals(30, appointment.durationMinutes);
    }


    public static void testClinicScheduler() {
        System.out.println("Running testClinicScheduler");
        ClinicScheduler scheduler = new ClinicScheduler("City Health Clinic");

        Assert.assertEquals(0, scheduler.getTotalAppointments());

        Patient p1 = new Patient("P001", "Alice", 30, "A+");
        Patient p2 = new Patient("P002", "Bob", 45, "B+");
        Patient p3 = new Patient("P003", "Charlie", 28, "O+");

        Appointment a1 = new Appointment(p1, "Dr. Smith", "2024-02-15", "10:00", "Checkup", 30);
        Appointment a2 = new Appointment(p2, "Dr. Johnson", "2024-02-15", "11:00", "Follow-up", 45);
        Appointment a3 = new Appointment(p3, "Dr. Smith", "2024-02-16", "09:00", "Emergency", 60);

        scheduler.addAppointment(a1);
        scheduler.addAppointment(a2);
        scheduler.addAppointment(a3);

        Assert.assertEquals(3, scheduler.getTotalAppointments());
        Assert.assertEquals(1, scheduler.getAppointmentsByType("Emergency"));

        Assert.assertEquals(45.0, scheduler.getAverageDuration(), 0.01);
        Assert.assertEquals(2, scheduler.getAppointmentsForDoctor("Dr. Smith"));
        Assert.assertEquals(2, scheduler.getAppointmentsOnDate("2024-02-15"));
    }

    public static void testFindBusiestDoctor() {
        System.out.println("Running testFindBusiestDoctor");
        ClinicScheduler scheduler = new ClinicScheduler("City Health Clinic");

        Assert.assertNull(scheduler.findBusiestDoctor());

        Patient p1 = new Patient("P001", "Patient1", 30, "A+");
        Patient p2 = new Patient("P002", "Patient2", 45, "B+");

        Appointment a1 = new Appointment(p1, "Dr. Smith", "2024-02-15", "10:00", "Checkup", 30);
        Appointment a2 = new Appointment(p1, "Dr. Johnson", "2024-02-15", "11:00", "Checkup", 30);
        Appointment a3 = new Appointment(p2, "Dr. Johnson", "2024-02-15", "12:00", "Checkup", 30);
        Appointment a4 = new Appointment(p2, "Dr. Johnson", "2024-02-16", "09:00", "Checkup", 30);
        Appointment a5 = new Appointment(p1, "Dr. Smith", "2024-02-16", "10:00", "Checkup", 30);
        Appointment a6 = new Appointment(p1, "Dr. Smith", "2024-02-16", "10:00", "Checkup", 30);

        scheduler.addAppointment(a1);
        scheduler.addAppointment(a2);
        scheduler.addAppointment(a3);
        scheduler.addAppointment(a4);
        scheduler.addAppointment(a5);
        scheduler.addAppointment(a6);


        Assert.assertArrayEquals(new Object[] {"Dr. Johnson", 3}, scheduler.findBusiestDoctor());
    }
}
