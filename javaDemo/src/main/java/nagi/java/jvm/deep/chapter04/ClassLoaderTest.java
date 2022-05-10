package nagi.java.jvm.deep.chapter04;

public class ClassLoaderTest {
    public static void main(String[] args) {
        // 获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader); // ClassLoaders$AppClassLoader@2437c6dc

        // 获取扩展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader); // ClassLoaders$PlatformClassLoader@34b7bfc0

        // 获取引导类加载器
        ClassLoader bootstrapLoader = extClassLoader.getParent();
        System.out.println(bootstrapLoader); // null

        // 数组类型加载：使用的类加载器与元素的加载器相同
        String[] strings = new String[10];
        System.out.println(strings.getClass().getClassLoader()); // null

        int[] ints = new int[10];
        System.out.println(ints.getClass().getClassLoader()); // null: 基本类型不需要加载
    }
}
