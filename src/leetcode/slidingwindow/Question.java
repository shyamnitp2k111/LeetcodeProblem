package leetcode.slidingwindow;

public class Question {

    //[1,1,1,1,1,1,0,0,0,0]


    //6

    //Once it is changed from 1 to 0, it will be always 0 in the collection
    //The collection is starting with 1

    //Plz find the first index of 0

    public static void main(String[] args) {
        int [] nums = {1,1,1,1,1,1,0,0,0,0};
        int index = findFirstIndexOfZero(nums);
        System.out.println(index);
    }

    private static int findFirstIndexOfZero(int[] nums) {

        for(int index = 0 ; index < nums.length ; index++) {
            if(nums[index] == 0){
                return index;
            }
        }
        return -1;

    }

}


