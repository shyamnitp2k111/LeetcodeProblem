package leetcodes.prefix;

import java.util.Arrays;

class Solution {


    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.garbageCollection(new String[]{"G","P","GP","GG"}, new int[]{2,4,3}));
    }

    public int garbageCollection(String[] garbage, int[] travel) {

        String[] listOfGarbageType = new String[] {"G", "P", "M"};

        int minutes = 0;

        for(int index = 0 ; index < listOfGarbageType.length ; index++ ) {

            String garbageType = listOfGarbageType[index];

            int minuteTravelling = 0;
            boolean flag = false;
            for(int house = 0 ; house < garbage.length ; house++) {
                if(garbage[house].contains(garbageType)) {
                    minutes++;
                }

                minuteTravelling += travel[index];
            }

        }

        return minutes;
    }
}