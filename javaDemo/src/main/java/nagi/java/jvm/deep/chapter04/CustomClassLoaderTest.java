package nagi.java.jvm.deep.chapter04;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        CustomClassLoader customClassLoader = new CustomClassLoader("E:\\IdeaProjects\\baseCode\\javaDemo\\src\\main\\java\\nagi\\java\\jvm\\deep\\chapter04\\");
        Class<?> user = customClassLoader.findClass("User");
        Method print = user.getMethod("print");
        Object o = user.getConstructor().newInstance();
        print.invoke(o);
        System.out.println(user.getClassLoader());
        System.out.println(user.getClassLoader().getParent());
    }
}
