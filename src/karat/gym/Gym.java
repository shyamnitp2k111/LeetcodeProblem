package karat.gym;

/**
We are building a program to manage a gym's membership. The gym has multiple members, each with a unique ID, name, and
membership status. The program allows gym staff to add new members, update members status, and get membership statistics.

Definitions:
* A "member" is an object that represents a gym member. It has properties for the ID, name, and membership status.
* A "membership" is a class which is used for managing members in the gym.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for Membership is not passing due to a bug in the code. Make the necessary changes to Membership to fix
     the bug.

We are currently updating our system to include information about workouts for our members. As part of this update, we
have introduced the Workout class, which represents a single workout session for a member. Each object of the Workout
class has a unique ID, as well as a start time and end time that are represented in the number of minutes spent from the
start of the day. You can assume that all the Workouts are from the same day.

To implement these changes, we need to add two functions to the Membership class:

2.1) The `addWorkout` function should be used to add a workout session for a member. If the given member does not exist
     while calling this function, the workout can be ignored.

2.2) The `getAverageWorkoutDurations` function should calculate the average duration of workouts for each member in
     minutes and return the results as a map.

To assist you in testing these new functions, we have provided the testGetAverageWorkoutDurations function.

We are developing a payment calculation system for our members. The payment amount is determined as follows:

- For members with BRONZE Membership:
  - The first workout is free.
  - From the second workout onwards, each hour costs $10.

- For members with SILVER Membership:
  - The first three workouts are free.
  - From the fourth workout onwards, each hour costs $8.

- For members with GOLD Membership:
  - The first five workouts are free.
  - From the sixth workout onwards, each hour costs $6.

The workouts are ordered by their ID.
The duration of each workout is always rounded up to the nearest hour. For example,
 if a person spent 80 minutes in a workout, they would be charged for 2 hours.

3) Implement a `getDuePayments` function, which returns a dictionary associating each member
 ID with their due payment.

To help you understand the requirements and test this new function, we have provided the `testGetDuePayments` function.
*/



import java.util.*;

import static org.junit.Assert.assertFalse;

enum MembershipStatus {
    /*
        Membership Status is of three types: BRONZE, SILVER and GOLD.
        BRONZE is the default membership a new member gets.
        SILVER and GOLD are paid memberships for the gym.
    */
    BRONZE,
    SILVER,
    GOLD
}

class Workout {
    /**
     * This class represents a single workout session for a member.
     * Each object of the Workout class has a unique ID, as well as
     * a start time and end time that are represented in the number
     * of minutes spent from the start of the day.
     */

    private int id;
    private int startTime;
    private int endTime;

    public Workout(int id, int startTime, int endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return endTime - startTime;
    }
}

class Member {
    /* Data about a gym member.*/
    public int memberId;
    public String name;
    public MembershipStatus membershipStatus;

    public Member(int memberId, String name, MembershipStatus membershipStatus) {
        this.memberId = memberId;
        this.name = name;
        this.membershipStatus = membershipStatus;
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId + ", Name: " + name + ", Membership Status: " + membershipStatus;
    }
}

class FreeRule {
    public int freeLimit;
    public int rate;

    public FreeRule(int freeLimit, int rate) {
        this.freeLimit = freeLimit;
        this.rate = rate;
    }
}

class Membership {
    /*
        Data for managing a gym membership, and methods which staff can
        use to perform any queries or updates.
    */
    public List<Member> members;
    public Map<Integer, List<Workout>> workMap;

    public Membership() {
        members = new ArrayList<>();
        workMap = new HashMap<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void updateMembership(int memberId, MembershipStatus membershipStatus) {
        for (Member member : members) {
            if (member.memberId == memberId) {
                member.membershipStatus = membershipStatus;
                break;
            }
        }
    }

    public MembershipStatistics getMembershipStatistics() {
        int totalMembers = members.size();
        int totalPaidMembers = 0;
        for (Member member : members) {
            if (member.membershipStatus != MembershipStatus.BRONZE) {
                totalPaidMembers++;
            }
        }
        double conversionRate = (totalPaidMembers / (double) totalMembers) * 100.0;
        return new MembershipStatistics(totalMembers, totalPaidMembers, conversionRate);
    }


    /**
     * Approach:
     * 1. First check if the given member ID exists in the members list.
     * 2. If the member does not exist, ignore the workout.
     * 3. If the member exists:
     *    - Use a HashMap (memberId -> List<Workout>) to store workouts.
     *    - If the memberId is not already present, create a new list using computeIfAbsent().
     *    - Add the workout to that member's workout list.
     *
     * Time Complexity: O(n) for existence check
     * Space Complexity: O(w) where w = total workouts stored
     */

    public void addWorkout(int memberId, Workout workout) {
        boolean exist = members.stream().anyMatch(member -> member.memberId == memberId);

        if (exist) {
            workMap.computeIfAbsent(memberId, k -> new ArrayList<>())
                    .add(workout);
        }
    }


    /**
     * Approach:
     * 1. Iterate over each entry in the workout map (memberId -> List<Workout>).
     * 2. For each member:
     *    - Convert workout list into stream of durations.
     *    - Use average() to compute average duration.
     *    - If no workouts exist, default to 0.
     * 3. Store result in a map (memberId -> averageDuration).
     *
     * Time Complexity: O(w) where w = total workouts
     * Space Complexity: O(m) where m = number of members with workouts
     */

    public Map<Integer, Double> getAverageWorkoutDurations() {

        Map<Integer, Double> result = new HashMap<>();

        for (Map.Entry<Integer, List<Workout>> entry : workMap.entrySet()) {
            double average = entry.getValue()
                    .stream()
                    .mapToInt(Workout::getDuration)
                    .average().orElse(0);
            result.put(entry.getKey(), average);
        }
        return result;
    }


    /**
     * Approach:
     * 1. Define rules for each membership type:
     *    - Free workout limit
     *    - Cost per hour
     *
     * 2. For each member:
     *    a. Get workouts list (if none → payment = 0).
     *    b. Sort workouts by ID (important as billing is ordered).
     *    c. Skip the free workouts based on membership.
     *    d. For remaining workouts:
     *       - Calculate duration.
     *       - Convert duration to hours using ceiling:
     *         hours = (duration + 59) / 60
     *       - Multiply by rate and add to total.
     *    e. Store total payment for the member.
     *
     * 3. Return map of (memberId -> totalPayment)
     *
     * Time Complexity:
     *    O(m * k log k) where k = workouts per member (due to sorting)
     *
     * Space Complexity:
     *    O(m) for result map
     */

    public Map<Integer, Integer> getDuePayments() {


        Map<Integer, Integer> paymentMap = new HashMap<>();
        Map<MembershipStatus, FreeRule> freeRuleMap = new HashMap<>();
        freeRuleMap.put(MembershipStatus.BRONZE, new FreeRule(1, 10));
        freeRuleMap.put(MembershipStatus.SILVER, new FreeRule(3, 8));
        freeRuleMap.put(MembershipStatus.GOLD, new FreeRule(5,6));

        for(Member member : members) {

            List<Workout> workoutList = workMap.getOrDefault(member.memberId, new ArrayList<>());

            if(workoutList.isEmpty()) {
                paymentMap.put(member.memberId, 0);
                continue;
            }

            workoutList.sort(Comparator.comparing(Workout::getId));
            FreeRule freeRule = freeRuleMap.get(member.membershipStatus);
            int total = 0;

            for(int index = freeRule.freeLimit; index < workoutList.size(); index++) {
                int duration = (int) Math.ceil((double)workoutList.get(index).getDuration()/ 60);
                total += duration * freeRule.rate;
            }

            paymentMap.put(member.memberId, total);
        }

        return paymentMap;






/*
        Map<Integer, Integer> result = new HashMap<>();

        Map<MembershipStatus, FreeRule> ruleMap = new HashMap<>();
        ruleMap.put(MembershipStatus.GOLD, new FreeRule(5, 6));
        ruleMap.put(MembershipStatus.SILVER, new FreeRule(3, 8));
        ruleMap.put(MembershipStatus.BRONZE, new FreeRule(1, 10));

        for (Member member : members) {
            int memberId = member.memberId;

            List<Workout> workouts = workMap.getOrDefault(memberId, new ArrayList<>());
            if (workouts.isEmpty()) {
                result.put(memberId, 0);
                continue;
            }

            // ✅ Sort by ID
            workouts.sort(Comparator.comparing(Workout::getId));
            FreeRule rule = ruleMap.get(member.membershipStatus);

            int freeLimit = rule.freeLimit;
            int rate = rule.rate;
            int total = 0;

            for (int i = freeLimit; i < workouts.size(); i++) {
                int duration = workouts.get(i).getDuration();
                total += calculateFees(duration, rate);
            }
            // ✅ Always put result
            result.put(memberId, total);
        }

        return result;*/
    }
    public int calculateFees(int duration, int rate) {
        int hours = (duration + 59) / 60;
        return hours * rate;
    }


}

class MembershipStatistics {
    /*
        Class for returning the getMembershipStatistics result
    */
    public int totalMembers;
    public int totalPaidMembers;
    public double conversionRate;

    public MembershipStatistics(int totalMembers, int totalPaidMembers, double conversionRate) {
        this.totalMembers = totalMembers;
        this.totalPaidMembers = totalPaidMembers;
        this.conversionRate = conversionRate;
    }
}

public class Gym {
    /*
        This is not a complete test suite, but tests some basic functionality of
        the code and shows how to use it.
    */
    public static void main(String[] args) {
      testMember();
      testMembership();
      testGetAverageWorkoutDurations();
      testGetDuePayments();
    }

    public static void testMember() {
        System.out.println("Running testMember");
        Member testMember = new Member(1, "John Doe", MembershipStatus.BRONZE);
        assert testMember.memberId == 1 :
                "Member ID should be 1, was " + testMember.memberId;
        assert testMember.name.equals("John Doe") :
                "Member name should be \"John Doe\", was \"" + testMember.name + "\"";
        assert testMember.membershipStatus == MembershipStatus.BRONZE :
                "Membership status should be BRONZE, was " + testMember.membershipStatus;
    }


    public static void testMembership() {
        System.out.println("Running testMembership");
        Membership testMembership = new Membership();
        Member testMember = new Member(1, "John Doe", MembershipStatus.BRONZE);
        testMembership.addMember(testMember);
        assert testMembership.members.size() == 1 :
                "Members size should be 1, was " + testMembership.members.size();
        assert testMembership.members.get(0).equals(testMember) :
                "First member should equal testMember";

        testMembership.updateMembership(1, MembershipStatus.SILVER);
        assert testMembership.members.get(0).membershipStatus == MembershipStatus.SILVER :
                "Membership status should be SILVER, was " + testMembership.members.get(0).membershipStatus;

        Member testMember2 = new Member(2, "Alex C", MembershipStatus.BRONZE);
        testMembership.addMember(testMember2);

        Member testMember3 = new Member(3, "Marie C", MembershipStatus.GOLD);
        testMembership.addMember(testMember3);

        Member testMember4 = new Member(4, "Joe D", MembershipStatus.SILVER);
        testMembership.addMember(testMember4);

        Member testMember5 = new Member(5, "June R", MembershipStatus.BRONZE);
        testMembership.addMember(testMember5);

        Member testMember6 = new Member(6, "Westley D", MembershipStatus.SILVER);
        testMembership.addMember(testMember6);

        MembershipStatistics attendanceStats = testMembership.getMembershipStatistics();
        assert attendanceStats.totalMembers == 6 :
                "Total members should be 6, was " + attendanceStats.totalMembers;
        assert attendanceStats.totalPaidMembers == 4 :
                "Total paid members should be 4, was " + attendanceStats.totalPaidMembers;
        assert Math.abs(attendanceStats.conversionRate - 66.67) < 0.1 :
                "Conversion rate should be 66.67, was " + attendanceStats.conversionRate;
    }


    public static void testGetAverageWorkoutDurations() {
        System.out.println("Running testGetAverageWorkoutDurations");
        Membership testMembership = new Membership();
        Member testMember1 = new Member(12, "John Doe", MembershipStatus.SILVER);
        testMembership.addMember(testMember1);

        Member testMember2 = new Member(22, "Alex Cleeve", MembershipStatus.BRONZE);
        testMembership.addMember(testMember2);

        Member testMember3 = new Member(31, "Marie Cardiff", MembershipStatus.GOLD);
        testMembership.addMember(testMember3);

        Member testMember4 = new Member(37, "George Costanza", MembershipStatus.SILVER);
        testMembership.addMember(testMember4);

        Workout testWorkout1 = new Workout(11, 10, 20);//10
        Workout testWorkout2 = new Workout(24, 15, 35);
        Workout testWorkout3 = new Workout(32, 45, 90);
        Workout testWorkout4 = new Workout(47, 100, 155);//55
        Workout testWorkout5 = new Workout(56, 120, 200);
        Workout testWorkout6 = new Workout(62, 300, 400);
        Workout testWorkout7 = new Workout(78, 1000, 1010);//10
        Workout testWorkout8 = new Workout(80, 1010, 1045);

        testMembership.addWorkout(12, testWorkout1);
        testMembership.addWorkout(22, testWorkout2);
        testMembership.addWorkout(31, testWorkout3);
        testMembership.addWorkout(12, testWorkout4);
        testMembership.addWorkout(22, testWorkout5);
        testMembership.addWorkout(31, testWorkout6);
        testMembership.addWorkout(12, testWorkout7);
        testMembership.addWorkout(4, testWorkout8);

        Map<Integer, Double> averageDurations = testMembership.getAverageWorkoutDurations();
        assert Math.abs(averageDurations.get(12) - 25.0) < 0.1 :
                "average duration for member 12 should be 25.0, was " + averageDurations.get(12);
        assert Math.abs(averageDurations.get(22) - 50.0) < 0.1 :
                "average duration for member 22 should be 50.0, was " + averageDurations.get(22);
        assert Math.abs(averageDurations.get(31) - 72.5) < 0.1 :
                "average duration for member 31 should be 72.5, was " + averageDurations.get(31);
        assertFalse(averageDurations.containsKey(4));
    }

    public static void testGetDuePayments() {
        System.out.println("Running testGetDuePayments");
        // Test get_due_payments function
        Membership testMembership = new Membership();
        testMembership.addMember(new Member(1, "John Doe", MembershipStatus.BRONZE));
        testMembership.addMember(new Member(2, "Alex C", MembershipStatus.SILVER));
        testMembership.addMember(new Member(3, "Marie C", MembershipStatus.GOLD));

        // Add workouts for members
        Map<Integer, List<Workout>> memberWorkouts = new HashMap<>();
        memberWorkouts.put(1, Arrays.asList(
                new Workout(1, 500, 700), new Workout(10, 300, 350), new Workout(12, 10, 20),
                new Workout(3, 50, 90), new Workout(6, 130, 150), new Workout(15, 900, 920)
        ));
        memberWorkouts.put(2, Arrays.asList(
                new Workout(13, 510, 540), new Workout(14, 600, 700), new Workout(2, 15, 35),
                new Workout(4, 100, 155), new Workout(18, 200, 225), new Workout(8, 1050, 1155)
        ));
        memberWorkouts.put(3, Arrays.asList(
                new Workout(5, 120, 135), new Workout(17, 140, 190), new Workout(9, 210, 255),
                new Workout(11, 400, 450), new Workout(16, 910, 940), new Workout(7, 1000, 1100)
        ));

        for (Map.Entry<Integer, List<Workout>> entry : memberWorkouts.entrySet()) {
            int memberId = entry.getKey();
            List<Workout> workoutList = entry.getValue();
            for (Workout workout : workoutList) {
                testMembership.addWorkout(memberId, workout);
            }
        }

        Map<Integer, Integer> duePayments = testMembership.getDuePayments();
        assert Math.abs(duePayments.get(1) - 50.0) < 0.1 :
                "due payment for member 1 should be 50.0, was " + duePayments.get(1);
        assert Math.abs(duePayments.get(2) - 32.0) < 0.1 :
                "due payment for member 2 should be 32.0, was " + duePayments.get(2);
        assert Math.abs(duePayments.get(3) - 6.0) < 0.1 :
                "due payment for member 3 should be 6.0, was " + duePayments.get(3);

        // Test member with no workouts
        testMembership.addMember(new Member(4, "Ron Burgundy", MembershipStatus.SILVER));
        Map<Integer, Integer> duePayments2 = testMembership.getDuePayments();
        assert Math.abs(duePayments2.get(4) - 0.0) < 0.1 :
                "due payment for member 4 should be 0.0, was " + duePayments2.get(4);
    }
}
