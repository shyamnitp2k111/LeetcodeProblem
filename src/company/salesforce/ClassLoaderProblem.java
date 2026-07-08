package company.salesforce;

public class ClassLoaderProblem {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader1 = ClassLoader.getPlatformClassLoader();
        Class<?> loadClass1 = classLoader1.loadClass("company.salesforce.LoadingClass");
        Object object1 = loadClass1.newInstance();
        LoadingClass loadingClass1 = (LoadingClass)object1;


        ClassLoader classLoader2 = ClassLoader.getPlatformClassLoader();
        Class<?> loadClass2 = classLoader2.loadClass("company.salesforce.LoadingClass");
        Object object2 = loadClass2.newInstance();
        LoadingClass loadingClass2 = (LoadingClass)object2;

        System.out.println(loadingClass1 == loadingClass2);


        /*CustomClassLoader loader1 = new CustomClassLoader();
        CustomClassLoader loader2 = new CustomClassLoader();

        Class<?> personClass1 = loader1.loadClass("com.example.Person");
        Class<?> personClass2 = loader2.loadClass("com.example.Person");

        Object person1 = personClass1.newInstance();
        Object person2 = personClass2.newInstance();


        Person p = (Person) person2;*/
    }
}
