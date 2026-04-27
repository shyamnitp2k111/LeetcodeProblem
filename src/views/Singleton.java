package views;

public class Singleton {

    int value;

    private final static Singleton singleton = new Singleton();



    static Singleton getSingletonObject() {
        return singleton;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
