package IRFramework;

import java.util.Arrays;
import java.util.Random;

class Solution {


    int[][] rects;
    int length ;
    Random random;

    public Solution(int[][] rects) {
        this.rects = rects;
        length = rects.length;
        random = new Random();
    }
    
    public int[] pick() {

        if(length == 0 ) {
            return null;
        }

        int generateNumberForRectangle ;
        if(length == 1) {
            generateNumberForRectangle = 0 ;
        } else {
            generateNumberForRectangle = random.nextInt(length );
        }

        System.out.println("rectange is ..."+ generateNumberForRectangle);


        int[] rectangle = rects[generateNumberForRectangle];

        int xGen = random.nextInt(rectangle[2] - rectangle[0] +1) + rectangle[0];
        int yGen = random.nextInt(rectangle[3] - rectangle[1] + 1) + rectangle[1];

        System.out.println(xGen +" "+yGen);
        return new int[]{xGen, yGen};
    }


    public static void main(String[] args) {
        Solution solution = new Solution(new int[][]{{-2, -2, 1, 1}, {2, 2, 4, 6}});
        Arrays.stream(solution.pick()).forEach(System.out::println);
    }
}