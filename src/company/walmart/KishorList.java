package company.walmart;

public class KishorList<T> {

    T [] a;
    private int capacity ;
    private int loadFactor = 2;
    int currentIndex = -1;

    public KishorList(int initialSize) {
        this.a = (T[]) new Object[initialSize];
        this.capacity = initialSize;
    }

    public void add(T val) {
        if(currentIndex == a.length -1) {
            //resize

            a = resize(a);

            a[++currentIndex] = val;
        } else {
            a[++currentIndex] = val;
        }
        currentIndex++;
    }

    //
    public boolean remove(int index1) {

        if(currentIndex == -1 && currentIndex < index1) {
            return false;
        } else {
            for(int index = index1; index < currentIndex; index++) {
                a[index] = a[index + 1 ];
            }

            currentIndex--;
            return true;
        }
    }



    private T[] resize(T[] a) {
        T[] newArray = (T[]) new Object[ loadFactor * a.length];

        for(int index = 0; index < newArray.length; index++) {
            newArray[index] = a[index];
        }
        return newArray;
    }

    public T getValue() {
        return a[currentIndex];
    }

}

class Mains{
    static void main() {
        KishorList<Integer> kishorList = new KishorList<>(10);
        kishorList.add(11);
       // System.out.println(kishorList.getValue());
        kishorList.remove(11);

        //System.out.println(kishorList.getValue());

 /*       Arrays.stream(kishorList.a, 0, kishorList.currentIndex + 1)
                .forEach(System.out::println);*/
    }
}
