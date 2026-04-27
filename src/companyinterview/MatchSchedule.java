package companyinterview;

public class MatchSchedule {
    public static void main(String[] args) {
        char[] teams = {'A','B','C','D','E','F','G','H'};

        int count = 0;

        for (int i = 0; i < teams.length; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                System.out.println(teams[i] + " vs " + teams[j]);
                count++;
            }
        }

        System.out.println("Total matches: " + count);
    }
}