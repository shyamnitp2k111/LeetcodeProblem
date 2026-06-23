package karat.gym1;


/**
We are building a program to manage a gym's membership. The gym has multiple members, each with a unique ID, name, and membership status. The program allows gym staff to add new members, update members status, and get membership statistics.
Definitions:
* A "member" is an object that represents a gym member. It has properties for the ID, name, and membership status.
* A "membership" is a class which is used for managing members in the gym.
To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for Membership is not passing due to a bug in the code. Make the necessary changes to Membership to fix the bug.


We are currently updating our system to include information about workouts for our members. As part of this update, we have introduced the Workout class, which represents a single workout session for a member. Each object of the Workout class has a unique ID, as well as a start time and end time that are represented in the number of minutes spent from the start of the day. You can assume that all the Workouts are from the same day.
To implement these changes, we need to add two functions to the Membership class:
2.1) The `addWorkout` function should be used to add a workout session for a member. If the given member does not exist while calling this function, the workout can be ignored.
2.2) The `getAverageWorkoutDurations` function should calculate the average duration of workouts for each member in minutes and return the results as a map.
To assist you in testing these new functions, we have provided the testGetAverageWorkoutDurations function.

3.We want to calculate pending membership fees for each member based on:

Membership type
Sequential workouts completed
Workout duration billed per hour (rounded up)
Billing rules:
🟤 BRONZE
First 1 workout FREE
Remaining workouts → $10 per hour
⚪ SILVER
First 3 workouts FREE
Remaining workouts → $8 per hour
🟡 GOLD
First 5 workouts FREE
Remaining workouts → $6 per hour
⏱ Any partial hour is billed as a full hour
Example: 61 minutes → 2 hours
*/



import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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

class Membership {
    /*
        Data for managing a gym membership, and methods which staff can
        use to perform any queries or updates.
    */
    public List<Member> members;
    public Map<Integer, List<Integer>> workMap;

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
        //  System.out.print(members.size());
        return new MembershipStatistics(totalMembers, totalPaidMembers, conversionRate);

    }

    public void addWorkout(int memberId, Workout workout) {

      /*for(Member member:members){
        if(member.memberId== memberId){

          List<Integer> workOuts= workMap.getOrDefault(memberId, new ArrayList<>());
          workOuts.add(workout.getDuration());
          workMap.put(memberId,workOuts);
          break;
        }
      }*/


        boolean exists = members.stream()
                .anyMatch(m -> Integer.valueOf(memberId).equals(m.memberId));

        if (exists) {
            List<Integer> workOuts = workMap.getOrDefault(memberId, new ArrayList<>());
            workOuts.add(workout.getDuration());
            workMap.put(memberId, workOuts);
        }
    }


    public Map<Integer, Double> getAverageWorkoutDurations() {

        Map<Integer, Double> result = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : workMap.entrySet()) {
            List<Integer> workOut = entry.getValue();

            double avg = workOut.stream().mapToDouble(w -> w).average().orElse(0.0);
            result.put(entry.getKey(), avg);
        }
        //System.out.println(result);
        return result;
    }

    public Map<Integer, Double> getPendingFees() {
        Map<Integer, Double> result = new HashMap<>();

        // Rule map
        Map<MembershipStatus, FeeRule> ruleMap = new HashMap<>();
        ruleMap.put(MembershipStatus.BRONZE, new FeeRule(1, 10));
        ruleMap.put(MembershipStatus.SILVER, new FeeRule(3, 8));
        ruleMap.put(MembershipStatus.GOLD, new FeeRule(5, 6));

        // ✅ memberId → Member lookup (O(1))
        Map<Integer, Member> memberMap = members.stream()
                .collect(Collectors.toMap(m -> m.memberId, m -> m));

        // ✅ Iterate only members with workouts
        for (Map.Entry<Integer, List<Integer>> entry : workMap.entrySet()) {
            Integer memberId = entry.getKey();

            List<Integer> workouts = entry.getValue();

            Member member = memberMap.get(memberId);
            if (member == null || workouts == null || workouts.isEmpty()) continue;

            FeeRule rule = ruleMap.get(member.membershipStatus);

            int limit = rule.freeLimit;
            int rate = rule.rate;
            double total = 0;

            // ✅ Skip free workouts
            for (int i = limit; i < workouts.size(); i++) {
                int duration = workouts.get(i);
                total += calculateFees(duration, rate);
            }

            if (total > 0) {
                result.put(memberId, total);
            }
        }
        return result;
    }

    private double calculateFees(int duration, int rate) {
        int hrs = (duration + 59) / 60;
        return hrs * rate;
    }
}

class FeeRule {
    int freeLimit;
    int rate;

    FeeRule(int freeLimit, int rate) {
        this.freeLimit = freeLimit;
        this.rate = rate;
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

public class GymUseCase {
    /*
        This is not a complete test suite, but tests some basic functionality of
        t
        he code and shows how to use it.
    */
  //  public static void main(String[] args) {
       /* testMember();
        testMembership();
        testGetAverageWorkoutDurations();
        testGetPendingFees();*/
   // }

    @Test
    public void testMember() {
        System.out.println("Running testMember");
        Member testMember = new Member(1, "John Doe", MembershipStatus.BRONZE);
        assert testMember.memberId == 1 :
                "member ID should be 1, was " + testMember.memberId;
        assert testMember.name.equals("John Doe") :
                "member name should be \"John Doe\", was \"" + testMember.name + "\"";
        assert testMember.membershipStatus == MembershipStatus.BRONZE :
                "membership status should be BRONZE, was " + testMember.membershipStatus;
    }

    @Test
    public void testMembership() {
        System.out.println("Running testMembership");
        Membership testMembership = new Membership();
        Member testMember = new Member(1, "John Doe", MembershipStatus.BRONZE);
        testMembership.addMember(testMember);
        assert testMembership.members.size() == 1 :
                "members size should be 1, was " + testMembership.members.size();
        assert testMembership.members.get(0).equals(testMember) :
                "first member should equal testMember";

        testMembership.updateMembership(1, MembershipStatus.SILVER);
        assert testMembership.members.get(0).membershipStatus == MembershipStatus.SILVER :
                "membership status should be SILVER, was " + testMembership.members.get(0).membershipStatus;

        Member testMember2 = new Member(2, "Alex C", MembershipStatus.BRONZE);
        testMembership.addMember(testMember2);

        Member testMember3 = new Member(3, "Marie C", MembershipStatus.GOLD);
        testMembership.addMember(testMember3);

        Member testMember4 = new Member(4, "Joe D", MembershipStatus.SILVER);
        testMembership.addMember(testMember4);

        Member testMember5 = new Member(5, "June R", MembershipStatus.BRONZE);
        testMembership.addMember(testMember5);

        MembershipStatistics attendanceStats = testMembership.getMembershipStatistics();
        assert attendanceStats.totalMembers == 5 :
                "total members should be 5, was " + attendanceStats.totalMembers;
        assert attendanceStats.totalPaidMembers == 3 :
                "total paid members should be 3, was " + attendanceStats.totalPaidMembers;
        assert Math.abs(attendanceStats.conversionRate - 60.00) < 0.1 :
                "conversion rate should be 60.00, was " + attendanceStats.conversionRate;
    }

    @Test
    public void testGetAverageWorkoutDurations() {
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

        Workout testWorkout1 = new Workout(11, 10, 20);
        Workout testWorkout2 = new Workout(24, 15, 35);
        Workout testWorkout3 = new Workout(32, 45, 90);
        Workout testWorkout4 = new Workout(47, 100, 155);
        Workout testWorkout5 = new Workout(56, 120, 200);
        Workout testWorkout6 = new Workout(62, 300, 400);
        Workout testWorkout7 = new Workout(78, 1000, 1010);
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

    @Test
    public void testGetPendingFees() {
        System.out.println("Running testGetPendingFees");
        Membership membership = new Membership();
        // Real-time member names
        Member bronzeMember = new Member(101, "Ravi Kumar", MembershipStatus.BRONZE);
        Member silverMember = new Member(202, "Anita Sharma", MembershipStatus.SILVER);
        Member goldMember = new Member(303, "Suresh Reddy", MembershipStatus.GOLD);
        membership.addMember(bronzeMember);
        membership.addMember(silverMember);
        membership.addMember(goldMember);
        /*
         * Define input using Map<Member, List<Workout>>
         */
        Map<Member, List<Workout>> workoutInput = new HashMap<>();
        // Bronze: 1 free workout, remaining are charged
        workoutInput.put(bronzeMember, List.of(
                new Workout(1, 10, 40),    // 30 min → FREE
                new Workout(2, 50, 111),   // 61 min → 2 hrs → $20
                new Workout(3, 150, 180)   // 30 min → 1 hr  → $10
        ));
        // Silver: 3 free workouts, remaining charged
        workoutInput.put(silverMember, List.of(
                new Workout(4, 10, 70),    // FREE
                new Workout(5, 80, 140),   // FREE
                new Workout(6, 150, 200),  // FREE
                new Workout(7, 210, 271)   // 61 min → 2 hrs → $16
        ));
        // Gold: first 5 workouts free, no charges
        workoutInput.put(goldMember, List.of(
                new Workout(8, 10, 100),
                new Workout(9, 120, 200),
                new Workout(10, 220, 260)
        ));
        /*
         * Reuse addWorkout() exactly like Test‑2
         */
        workoutInput.forEach((member, workouts) -> {
            for (Workout workout : workouts) {
                membership.addWorkout(member.memberId, workout);
            }
        });
        /*
         * Call new method (implementation pending)
         */
        Map<Integer, Double> pendingFees = membership.getPendingFees();
        assert Math.abs(pendingFees.get(101) - 30.0) < 0.1 :
                "Pending fee for Ravi Kumar should be 30.0, was " + pendingFees.get(101);
        assert Math.abs(pendingFees.get(202) - 16.0) < 0.1 :
                "Pending fee for Anita Sharma should be 16.0, was " + pendingFees.get(202);
        assert !pendingFees.containsKey(303) :
                "Suresh Reddy (Gold member) should have no pending fees";
    }
}
