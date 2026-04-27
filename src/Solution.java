import java.util.*;
import java.util.stream.Collectors;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] result = solution.onesMinusZeros(new int[][]{{}});
    }


    public int[][] onesMinusZeros(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;
        int[][] results = new int[rows][cols];
        Map<String, Integer> map = new HashMap<>();

        for(int row = 0 ; row < rows; row++) {
            int sumRow = 0;
            int sumCol = 0;
            for(int col = 0; col < cols; col++) {
                 sumRow += grid[row][col];

                 sumCol += grid[col][row];
            }

            map.put("row-"+row, sumRow);
            map.put("col-"+row, sumCol);
        }


        for(int row = 0 ; row < row; row++) {
            for (int col = 0; col < cols; col++) {
                results[row][col] = map.get("row-"+row) + map.get("col-"+col)
                        - (row - map.get("row-"+row)) -  (cols - map.get("col-"+col));
            }
        }
        return results;

    }
}