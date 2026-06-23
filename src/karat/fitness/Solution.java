package karat.fitness;

/*
We are developing a fitness tracking application that monitors workout sessions, exercises, and user progress.

The program includes three classes: `Exercise`, `WorkoutSession`, and `UserProfile`.

Classes:
* The `Exercise` class represents a specific exercise performed during a workout.
* The `WorkoutSession` class holds information about a complete workout session.
* The `UserProfile` class manages all workout sessions for a user and provides statistics.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for UserProfile is not passing due to a bug in the code. Make the necessary changes to UserProfile to fix the bug.
*/

/*
2) We want to add a new function called "getWeeklyProgressComparison" to the UserProfile class.
This function compares the total calories burned in the most recent week with the previous week
and returns the percentage change.

For example, if:
- Week 1 (2024-01-01 to 2024-01-07): Total 2000 calories
- Week 2 (2024-01-08 to 2024-01-14): Total 2500 calories

The function should return 25.0 (representing a 25% increase).
If the second week burned fewer calories, return a negative percentage.

The function should return an array [percentageChange, week1Calories, week2Calories].
If there aren't enough sessions for two weeks, return null.

To assist you in testing this new function, we have provided the testGetWeeklyProgressComparison function.
*/

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import org.junit.*;

class Exercise {
    /** Data about a specific exercise. */
    String name;
    String category;        // "Cardio", "Strength", "Flexibility", "Sports"
    int durationMinutes;
    int caloriesBurned;

    Exercise(String name, String category, int durationMinutes, int caloriesBurned) {
        this.name = name;
        this.category = category;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }
}

class WorkoutSession {
    /** Data about a complete workout session. */
    List<Exercise> exercises;
    String date;            // Format: "YYYY-MM-DD"
    String startTime;       // Format: "HH:MM"
    
    WorkoutSession(List<Exercise> exercises, String date, String startTime) {
        this.exercises = exercises;
        this.date = date;
        this.startTime = startTime;
    }

    int getTotalDuration() {
        return exercises.stream().mapToInt(ex -> ex.durationMinutes).sum();
    }

    int getTotalCalories() {
        return exercises.stream().mapToInt(ex -> ex.caloriesBurned).sum();
    }
}

class UserProfile {
    /**
     * Manages all workout sessions for a user and provides statistics.
     */
    ArrayList<WorkoutSession> sessions = new ArrayList<>();
    String userId;
    String userName;

    UserProfile(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    void addSession(WorkoutSession session) {
        sessions.add(session);
    }

    int getTotalSessions() {
        return sessions.size();
    }

    int getTotalCaloriesBurned() {
        return sessions.stream().mapToInt(session -> session.getTotalCalories()).sum();
    }

    int getTotalWorkoutMinutes() {
        return sessions.stream().mapToInt(session -> session.getTotalDuration()).sum();
    }

    double getAverageCaloriesPerSession() {
        /** 
         * Returns the average calories burned per session.
         * BUG: This method has a bug - fix it!
         */

        if(getTotalSessions() == 0) {
            return 0.0;
        }

        //return getTotalCaloriesBurned() / getTotalSessions();

       return (double) getTotalCaloriesBurned() / getTotalSessions();

        /*if(Double.isNaN(average)) {
            return 0.0;
        }*/

        //return average;
    }

    int getSessionsByCategory(String category) {
        /** Returns count of sessions that included at least one exercise from the specified category. */
        return (int) sessions.stream()
            .filter(session -> session.exercises.stream()
                .anyMatch(ex -> ex.category.equals(category)))
            .count();
    }

    public Object[] getWeeklyProgressComparisons() {

        if (sessions.isEmpty()) {
            return null;
        }

        LocalDate latestDate = sessions.stream()
                .map(session -> LocalDate.parse(session.date))
                .max(LocalDate::compareTo)
                .orElse(null);

        LocalDate currentWeekStart = latestDate.minusDays(6);
        LocalDate previousWeekStart = latestDate.minusDays(13);

        int week2Calories = sessions.stream()
                .filter(session -> {
                    LocalDate date = LocalDate.parse(session.date);
                    return !date.isBefore(currentWeekStart)
                            && !date.isAfter(latestDate);
                })
                .mapToInt(WorkoutSession::getTotalCalories)
                .sum();

        int week1Calories = sessions.stream()
                .filter(session -> {
                    LocalDate date = LocalDate.parse(session.date);
                    return !date.isBefore(previousWeekStart)
                            && date.isBefore(currentWeekStart);
                })
                .mapToInt(WorkoutSession::getTotalCalories)
                .sum();

        if (week1Calories == 0) {
            return null;
        }

        double percentageChange =
                ((double) (week2Calories - week1Calories) / week1Calories) * 100;

        return new Object[]{
                percentageChange,
                week1Calories,
                week2Calories
        };
    }


    public Object[] getWeeklyProgressComparison() {

        if(sessions.isEmpty()) {
            return null;
        }

        LocalDate latestDate = sessions.stream()
                .map(s -> LocalDate.parse(s.date))
                .max(LocalDate::compareTo).get();

        LocalDate currentWeekStart = latestDate.minusDays(6);
        LocalDate prevWeekStart = latestDate.minusDays(13);

        int col1 = sessions.stream().filter(s -> {
            LocalDate date = LocalDate.parse(s.date);
            return !date.isBefore(currentWeekStart) && !date.isAfter(latestDate);
        }).mapToInt(WorkoutSession::getTotalCalories)
                .sum();

        int col2 = sessions.stream().filter(s -> {
            LocalDate date = LocalDate.parse(s.date);
            return !date.isBefore(prevWeekStart) && !date.isAfter(currentWeekStart);
        }).mapToInt(WorkoutSession::getTotalCalories)
                .sum();

        double percentage = ((double)(col1 - col2)/col2) * 100;

        return new Object[]{percentage, col2, col1};
    }
}

public class Solution {
    public static void main(String[] args) {
        testWorkoutSession();
        testUserProfile();
        testGetWeeklyProgressComparison();
    }

    public static void testWorkoutSession() {
        System.out.println("Running testWorkoutSession");
        Exercise ex1 = new Exercise("Running", "Cardio", 30, 300);
        Exercise ex2 = new Exercise("Push-ups", "Strength", 15, 100);
        
        WorkoutSession session = new WorkoutSession(Arrays.asList(ex1, ex2), "2024-02-01", "07:00");

        Assert.assertEquals(45, session.getTotalDuration());
        Assert.assertEquals(400, session.getTotalCalories());
    }

    public static void testUserProfile() {
        System.out.println("Running testUserProfile");
        UserProfile profile = new UserProfile("U001", "John Doe");

        Assert.assertEquals(0, profile.getTotalSessions());
        Assert.assertEquals(0, profile.getTotalCaloriesBurned());
        Assert.assertEquals(0.0, profile.getAverageCaloriesPerSession(), 0.1);

        Exercise ex1 = new Exercise("Running", "Cardio", 30, 300);
        Exercise ex2 = new Exercise("Cycling", "Cardio", 45, 400);
        Exercise ex3 = new Exercise("Yoga", "Flexibility", 60, 200);

        WorkoutSession s1 = new WorkoutSession(Arrays.asList(ex1), "2024-02-01", "07:00");
        WorkoutSession s2 = new WorkoutSession(Arrays.asList(ex2), "2024-02-02", "08:00");
        WorkoutSession s3 = new WorkoutSession(Arrays.asList(ex3), "2024-02-03", "19:00");

        profile.addSession(s1);
        profile.addSession(s2);
        profile.addSession(s3);

        Assert.assertEquals(3, profile.getTotalSessions());
        Assert.assertEquals(900, profile.getTotalCaloriesBurned());
        Assert.assertEquals(135, profile.getTotalWorkoutMinutes());
        Assert.assertEquals(300.0, profile.getAverageCaloriesPerSession(), 0.01);
        Assert.assertEquals(2, profile.getSessionsByCategory("Cardio"));
    }

    public static void testGetWeeklyProgressComparison() {
        System.out.println("Running testGetWeeklyProgressComparison");
        UserProfile profile = new UserProfile("U001", "John Doe");

        Assert.assertNull(profile.getWeeklyProgressComparison());

        // This test requires more complex date-based logic
        // Students should implement week calculation and comparison


        Exercise ex1 = new Exercise("Running", "Cardio", 30, 300);
        Exercise ex2 = new Exercise("Cycling", "Cardio", 45, 400);
        Exercise ex3 = new Exercise("Yoga", "Flexibility", 60, 200);

        WorkoutSession s1 = new WorkoutSession(Arrays.asList(ex1), "2026-05-21", "07:00");
        WorkoutSession s2 = new WorkoutSession(Arrays.asList(ex2), "2026-05-18", "08:00");
        WorkoutSession s3 = new WorkoutSession(Arrays.asList(ex3), "2026-05-12", "19:00");

        profile.addSession(s1);
        profile.addSession(s2);
        profile.addSession(s3);


        Assert.assertArrayEquals(new Object[]{250.00, 200, 700}, profile.getWeeklyProgressComparison());
        System.out.println("Note: Implement week-based comparison logic");
    }
}
