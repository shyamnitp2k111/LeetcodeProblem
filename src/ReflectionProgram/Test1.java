package ReflectionProgram;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> className = Class.forName("ReflectionProgram.Logic1");

        Object obj = className.getDeclaredConstructor().newInstance();

        int x =100;
        Method method = className.getMethod("m", int.class);

        Object returnValue = method.invoke(obj, 1,2);

        System.out.println("return value .... "+ returnValue);
    }
}


class Logic1{

    int x ;

    public String m(int x){
        System.out.println("Hi ");
        return "Shyam";
    }
}
