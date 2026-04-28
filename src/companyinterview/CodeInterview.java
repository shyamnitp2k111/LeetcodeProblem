package companyinterview;

public class CodeInterview {
    public static void main(String[] args) {

        //wissen
        int[] array = {1,2,0};

        int max = Math.max(array[0], array[1]);
        int xorValue = array[0]^ array[1];

        for(int index = 2 ; index < array.length ; index++) {
            if(array[index] > 0 && max < array[index]) {
                max = array[index];
            }

            if(array[index] > 0) {
                xorValue = xorValue ^ array[index];
            }
        }


        boolean flag = false;
        for(int index = 1 ; index <= max; index++) {
            if((xorValue ^ index) != 0 ) {
                System.out.println("inside ..."+index);
                flag = true;
                break;
            }
        }


        if(!flag) {
            System.out.println(max + 1);
        }

    }
}
