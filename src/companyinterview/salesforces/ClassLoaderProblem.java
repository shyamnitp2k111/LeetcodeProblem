package companyinterview.salesforces;

public class ClassLoaderProblem {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader1 = ClassLoader.getPlatformClassLoader();
        Class<?> loadClass1 = classLoader1.loadClass("companyinterview.salesforces.LoadingClass");
        Object object1 = loadClass1.newInstance();
        LoadingClass loadingClass1 = (LoadingClass)object1;


        ClassLoader classLoader2 = ClassLoader.getPlatformClassLoader();
        Class<?> loadClass2 = classLoader2.loadClass("companyinterview.salesforces.LoadingClass");
        Object object2 = loadClass2.newInstance();
        LoadingClass loadingClass2 = (LoadingClass)object2;

        System.out.println(loadingClass1 == loadingClass2);
    }
}
