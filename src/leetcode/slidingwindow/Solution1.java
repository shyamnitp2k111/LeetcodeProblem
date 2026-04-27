package leetcode.slidingwindow;

class Solution1 {
    public int[] decrypt(int[] code, int k) {

        int sum = 0;
        int[] results = new int[code.length];
        for(int index = 1 ; index <= k ; index++ ) {
            sum += code[index];
        }

        results[0] = sum;
        for(int index = 1 ; index < code.length ; index++) {
            if(index+k > code.length) {
                index =
                sum - code[index];
            }
        }
      return null;
    }

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        solution1.decrypt(new int[]{1,2 ,3}, 2);
    }
}