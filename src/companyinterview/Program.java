package companyinterview;

public class Program {


    public static void main(String[] args) {
        int[] array = {7,8,9,1,3,5};
        System.out.println(binarySearchs(array, 4));

        System.out.println(isArraySorted(array));
    }

    private static boolean isArraySorted(int[] array) {

        int start = 0;
        int end = array.length -1;
        int mid =  (start + end )/ 2;
        /*if(array[start] < array[mid] && array[mid] < array[end]) {
            return true;
        } else if(array[start] > array[mid] && array[mid] > array[end]) {
            return true;
        } else if{*/
            // rotated
            int index = findPrivotInGivenArray(array);

            System.out.println(array[index]);
            if(index == -1) {
                return false;
            }

            //{7,8,9,1,3,5};
            return array[index - 1] > array[0] && array[index] < array[array.length - 1];

    }

    private static int findPrivotInGivenArray(int[] array) {

        int start = 0;
        int end = array.length -1;

        while(start <= end) {
            int mid  = (start + end) / 2;

            //{7,8,9,1,3,5};
            if(array[mid] < array[mid+1] && array[mid] < array[mid -1]) {
                return mid;
            } else if(array[mid] < array[end]) {
                end = mid -1;
            } else if(array[mid] > array[end]) {
                start = mid +1;
            }
        }

        return -1;
    }


    private static boolean binarySearchs(int[] array, int target) {

          int start = 0;
          int ends = array.length -1;

          while(start <= ends) {
              int mid = (start + ends) /2;

              if(array[mid] == target) {
                  return true;
              } else if(array[mid] > array[start]) {
                 //first half is sorted
                  if(array[start] < target && array[mid] > target) {
                      ends = mid -1;
                  } else {
                      start = mid +1;
                  }

              } else {
                  //second half is sorted
                  if(array[mid] < target && array[ends] > target) {
                      start = mid + 1;
                  } else {
                      ends = mid  - 1;
                  }
              }
          }

          return false;
    }
}
