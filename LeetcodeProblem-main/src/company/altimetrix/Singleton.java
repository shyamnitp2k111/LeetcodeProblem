package company.altimetrix;

import java.io.ObjectStreamException;
import java.io.Serializable;

public final class Singleton implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    // volatile is required for double-checked locking
    private static volatile Singleton instance;

    private Singleton() {

        // Prevent Reflection
        if (instance != null) {
            throw new RuntimeException("Use getInstance() instead.");
        }
    }

    public static Singleton getInstance() {

        if (instance == null) {

            synchronized (Singleton.class) {

                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    // Prevent Serialization from creating another object
    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }

    // Prevent Cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned.");
    }
}

class MainClass{
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        System.out.println(s1);
        Singleton s2 = Singleton.getInstance();
        System.out.println(s2);
    }
}


