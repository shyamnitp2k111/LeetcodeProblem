package karat.classroom;

import java.io.*;
import java.util.*;

/**
 * We are developing an educational platform that tracks students, assignments, and grades.
 * <p>
 * The program includes three classes: `Student`, `Assignment`, and `GradeBook`.
 * <p>
 * Classes:
 * The `Student` class represents a student in the system.
 * The `Assignment` class holds information about a graded assignment.
 * The `GradeBook` class manages all assignments and provides grade analytics.
 * <p>
 * To begin with, we present you with two tasks:
 * 1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
 * 1-2) The test for GradeBook is not passing due to a bug in the code. Make the necessary changes to GradeBook to fix the bug.
 * <p>
 * <p>
 * 2) We want to add a new function called "getImprovementTrend" to the GradeBook class.
 * This function analyzes a student's grades over time and determines if they are improving,
 * declining, or stable.
 * <p>
 * The function should:
 * - Sort assignments by submission date
 * - Compare the average of the first half with the average of the second half
 * - Return "Improving" if second half average is at least 5% higher
 * - Return "Declining" if second half average is at least 5% lower
 * - Return "Stable" otherwise
 * <p>
 * For example:
 * Student has 6 assignments with scores:
 * First half (assignments 1-3): 70, 75, 72 → average = 72.33
 * Second half (assignments 4-6): 80, 85, 82 → average = 82.33
 * Improvement = (82.33 - 72.33) / 72.33 = 13.8% → Return "Improving"
 * <p>
 * Return format: [trend, firstHalfAvg, secondHalfAvg]
 * <p>
 * To assist you in testing this new function, we have provided the testGetImprovementTrend function.
 */




import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Student {
    /**
     * Data about a student.
     */
    String studentId;
    String name;
    String grade;       // "Freshman", "Sophomore", "Junior", "Senior"

    Student(String studentId, String name, String grade) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Student student = (Student) other;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}

class Assignment {
    /**
     * Data about a graded assignment.
     */
    Student student;
    String assignmentName;
    double score;           // Score out of 100
    String submissionDate;  // Format: "YYYY-MM-DD"
    String subject;         // "Math", "Science", "English", etc.

    Assignment(Student student, String assignmentName, double score, String submissionDate, String subject) {
        this.student = student;
        this.assignmentName = assignmentName;
        this.score = score;
        this.submissionDate = submissionDate;
        this.subject = subject;
    }
}

class GradeBook {
    /**
     * Manages assignments and provides grade analytics.
     */
    ArrayList<Assignment> assignments = new ArrayList<>();
    String className;

    GradeBook(String className) {
        this.className = className;
    }

    void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    int getTotalAssignments() {
        return assignments.size();
    }

    double getAverageScore() {
        /**
         * Returns the average score across all assignments.
         * BUG: This method has a bug - fix it!
         */
        return assignments.stream().mapToDouble(value -> value.score).average().orElse(0.0);
    }

    double getStudentAverage(String studentId) {
        /** Returns the average score for a specific student. */
        List<Assignment> studentAssignments = assignments.stream()
                .filter(a -> a.student.studentId.equals(studentId))
                .collect(Collectors.toList());

        if (studentAssignments.isEmpty()) return 0.0;

        double total = studentAssignments.stream().mapToDouble(a -> a.score).sum();
        return total / studentAssignments.size();
    }

    int getAssignmentsBySubject(String subject) {
        /** Returns count of assignments for a specific subject. */
        return (int) assignments.stream()
                .filter(a -> a.subject.equals(subject))
                .count();
    }


    /**
     * Approach:
     * 1. First, I will filter all assignments belonging to the given student.
     * 2. Then, I will sort those assignments by submission date to ensure chronological order.
     * 3. Next, I will divide the list into two halves:
     * - First half → earlier assignments
     * - Second half → recent assignments
     * 4. I will calculate the average score for both halves.
     * 5. Then, I compute the percentage change between the two averages.
     * 6. Based on the percentage:
     * - ≥ +5% → Improving
     * - ≤ -5% → Declining
     * - Otherwise → Stable
     * 7. Finally, I return the trend along with both averages.
     */

    public Object[] getImprovementTrend(String studentId) {
        List<Assignment> studentAssignments = assignments.stream()
                .filter(a -> a.student.studentId.equals(studentId))
                .sorted(Comparator.comparing(a -> a.submissionDate))
                .collect(Collectors.toList());

        if (studentAssignments == null || studentAssignments.isEmpty()) {
            return new Object[]{"Stable", 0.0, 0.0};
        }

        int n = studentAssignments.size();
        int mid = n / 2;

        double firstAverage = studentAssignments.subList(0, mid).stream()
                .mapToDouble(a -> a.score)
                .average().orElse(0);

        double secondAverage = studentAssignments.subList(mid, n).stream()
                .mapToDouble(a -> a.score)
                .average()
                .orElse(0);

        String trend;
        double percentage = ((secondAverage - firstAverage) / firstAverage) * 100;
        if (percentage >= 5.0) {
            trend = "Improving";
        } else if(percentage <= -5.0) {
            trend = "Declining";
        } else {
            trend = "Stable";
        }
        return new Object[] {trend, firstAverage, secondAverage};
    }

/*
    public Object[] getImprovementTrend(String studentId) {

        */
/** ✅ Step 1: Filter assignments for given student
 ✅ Step 2: Sort them by submission date (chronological order) *//*

        List<Assignment> studentAssignments = assignments.stream()
                .filter(a -> a.student.studentId.equals(studentId))   // filter by student
                .sorted(Comparator.comparing(a -> a.submissionDate))  // sort by date
                .collect(Collectors.toList());

        */
/** ✅ Step 3: Handle edge case (no assignments) *//*

        if (studentAssignments.isEmpty()) {
            return new Object[]{"Stable", 0.0, 0.0};
        }

        */
/** ✅ Step 4: Divide into two halves *//*

        int n = studentAssignments.size();
        int mid = n / 2;

        */
/** ✅ Step 5: Calculate average of first half (earlier performance) *//*

        double firstHalfAverage = studentAssignments.subList(0, mid).stream()
                .mapToDouble(a -> a.score)
                .average()
                .orElse(0.0);

        */
/** ✅ Step 6: Calculate average of second half (recent performance) *//*

        double secondHalfAverage = studentAssignments.subList(mid, n).stream()
                .mapToDouble(a -> a.score)
                .average()
                .orElse(0.0);

        */
/** ✅ Step 7: Calculate percentage change between halves *//*

        double percentage = ((secondHalfAverage - firstHalfAverage) / firstHalfAverage) * 100;

        */
/** ✅ Step 8: Decide trend based on threshold (±5%) *//*

        String trend;

        if (percentage >= 5.0) {
            trend = "Improving";     */
/** performance improved *//*

        } else if (percentage <= -5.0) {
            trend = "Declining";     */
/** performance decreased *//*

        } else {
            trend = "Stable";        */
/** minimal change *//*

        }

        */
/** ✅ Step 9: Return result *//*

        return new Object[]{trend, firstHalfAverage, secondHalfAverage};
    }
*/
}

public class Classroom {
/*    public static void main(String[] args) {
        testAssignment();
        testGradeBook();
        testGetImprovementTrend();
    }*/

    @Test
    public void testAssignment() {
        System.out.println("Running testAssignment");
        Student student = new Student("S001", "Alice", "Junior");
        Assignment assignment = new Assignment(student, "Homework 1", 85.0, "2024-02-01", "Math");

        Assert.assertEquals(student, assignment.student);
        Assert.assertEquals("Homework 1", assignment.assignmentName);
        Assert.assertEquals(85.0, assignment.score, 0.01);
    }

    @Test
    public void testGradeBook() {
        System.out.println("Running testGradeBook");
        GradeBook gradeBook = new GradeBook("Math 101");

        Assert.assertEquals(0, gradeBook.getTotalAssignments());

        Student s1 = new Student("S001", "Alice", "Junior");
        Student s2 = new Student("S002", "Bob", "Senior");

        Assignment a1 = new Assignment(s1, "HW1", 85.0, "2024-02-01", "Math");
        Assignment a2 = new Assignment(s1, "HW2", 90.0, "2024-02-05", "Math");
        Assignment a3 = new Assignment(s2, "HW1", 75.0, "2024-02-01", "Math");

        gradeBook.addAssignment(a1);
        gradeBook.addAssignment(a2);
        gradeBook.addAssignment(a3);

        Assert.assertEquals(3, gradeBook.getTotalAssignments());
        Assert.assertEquals(83.33, gradeBook.getAverageScore(), 0.01);
        Assert.assertEquals(87.5, gradeBook.getStudentAverage("S001"), 0.01);
        Assert.assertEquals(3, gradeBook.getAssignmentsBySubject("Math"));
    }

    @Test
    public void testGetImprovementTrend() {
        System.out.println("Running testGetImprovementTrend");
        GradeBook gradeBook = new GradeBook("Math 101");

        Student s1 = new Student("S001", "Alice", "Junior");

        Assignment a1 = new Assignment(s1, "HW1", 70.0, "2024-02-01", "Math");
        Assignment a2 = new Assignment(s1, "HW2", 75.0, "2024-02-05", "Math");
        Assignment a3 = new Assignment(s1, "HW3", 72.0, "2024-02-10", "Math");
        Assignment a4 = new Assignment(s1, "HW4", 80.0, "2024-02-15", "Math");
        Assignment a5 = new Assignment(s1, "HW5", 85.0, "2024-02-20", "Math");
        Assignment a6 = new Assignment(s1, "HW6", 82.0, "2024-02-25", "Math");

        gradeBook.addAssignment(a1);
        gradeBook.addAssignment(a2);
        gradeBook.addAssignment(a3);
        gradeBook.addAssignment(a4);
        gradeBook.addAssignment(a5);
        gradeBook.addAssignment(a6);

        Object[] result = gradeBook.getImprovementTrend("S001");
        System.out.println();
        Assert.assertEquals("Improving", result[0]);
        Assert.assertEquals(72.33, (Double) result[1], 0.1);
        Assert.assertEquals(82.33, (Double) result[2], 0.1);
    }
}

