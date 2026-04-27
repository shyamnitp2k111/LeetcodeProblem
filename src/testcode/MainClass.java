package testcode;

public class MainClass {

    public static void main(String[] args) {
        SystemClockVerifier systemClockVerifier = new SystemClockVerifier();
        System.out.println("----------- value ---------"+ systemClockVerifier.isClockChanged());
    }
}
