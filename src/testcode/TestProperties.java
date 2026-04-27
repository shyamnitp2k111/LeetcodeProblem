package testcode;

import java.util.Properties;

public class TestProperties {

    public static void main(String[] args) {
        Properties properties = new Properties();
        long results = intValueOf("stress.process.properties.waittime.max", properties);
        System.out.println(results);

    }


    public static long longValueOf(String key, String defaultValue, Properties p) {
        return Long.parseLong(p.getProperty(key, defaultValue));
    }

    public static long longValueOf(String key, Properties p) {
        return longValueOf(key, null, p);
    }


    public static int intValueOf(String key, Properties p) {
        return intValueOf(key, null, p);
    }

    /**
     * Converts the value of the property with key <code>key</code> into an int.
     * @param key The key of the property
     * @param defaultValue the default value
     * @param p The properties to fetch the value from
     * @return the int representation
     */
    public static int intValueOf(String key, String defaultValue, Properties p) {
        return Integer.parseInt(p.getProperty(key, defaultValue));
    }

}
