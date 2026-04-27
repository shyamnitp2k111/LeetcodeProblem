package companyinterview;

import java.util.*;

public class WexCodingProblem {

    public static void main(String[] args) {
        //Input: s = "barfoothefoobarman", words = ["foo","bar"]

        /*String[] words = {"foo","bar"};
        String s = "barfoothefoobarman";
        Map<String, Integer> map = new HashMap<>();

        int minLength = Integer.MAX_VALUE;

        for(String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
            minLength  = Math.min(minLength, word.length());
        }

        List<Integer> list = new ArrayList<>();
        for(int index = 0 ; index < s.length(); index++) {

            int temp = index;
            while (map.containsKey(s.substring(index, index+minLength))) {
                map.put(s.substring(index, index+minLength), map.get(s.substring(index, index+minLength)) -1);
                index = index+ minLength;
            }


            for(Map.Entry<String, Integer> entry : map.entrySet()) {
                if(entry.getValue() != 0) {
                    index = temp;
                    continue;
                }
            }

            list.add(temp);




        }*/

        WexCodingProblem solution = new WexCodingProblem();
        System.out.println(solution.searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,60}}, 3));

    }

    public boolean searchMatrix(int[][] matrix, int target) {

        int start = 0;
        int end = matrix.length -1;
        int rowNumber = -1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if(matrix[mid][0] <= target && matrix[mid][matrix[mid].length-1] >= target) {
                rowNumber = mid;
                break;
            } else if(matrix[mid][0] > target && matrix[mid][matrix[mid].length-1] > target){
                end = mid -1;
            } else if(matrix[mid][0] < target && matrix[mid][matrix[mid].length-1] < target) {
                start = mid + 1;
            }
        }

        // Row not found
        if (rowNumber == -1) return false;

        start = 0;
        end = matrix[rowNumber].length -1 ;
        while (start <= end) {
            int mid = start + (end - start) / 2;

            if(matrix[rowNumber][mid] == target) {
                return true;
            } else if(matrix[rowNumber][mid] > target){
                end = mid -1;
            } else if(matrix[rowNumber][mid] < target) {
                start = mid + 1;
            }
        }

        return false;
    }
}
