package companyinterview;

public final class Singletion {

    final int id;
    final String date;

    private static Singletion singletion;

    private Singletion(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public static Singletion getSingletion() {

        if(null == singletion) {

            synchronized (Singletion.class) {

                if(null == singletion) {
                    singletion = new Singletion(1, "shyam");
                }
            }
        }

        return singletion;
    }
}
