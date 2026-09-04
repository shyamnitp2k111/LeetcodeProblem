package designpattern.creational;

class Singleton {

    static private Singleton singleton = null;
    private String data;

    private Singleton(String data) {
        this.data = data;
    }

    public static Singleton getInstance() {
        if(singleton == null) {
            synchronized (Singleton.class) {

                if(singleton == null) {
                    singleton = new Singleton("Shyam");
                }
            }
        }

        return singleton;
    }

    public String getData() {
        return data;
    }
}

class Client {

    static void main() {
        Singleton singletonFirst = Singleton.getInstance();
        System.out.println(singletonFirst);
        System.out.println(singletonFirst.getData());

        Singleton singletonSecond = Singleton.getInstance();
        System.out.println(singletonSecond);
        System.out.println(singletonSecond.getData());
    }
}
