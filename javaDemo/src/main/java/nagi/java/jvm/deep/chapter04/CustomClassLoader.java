package nagi.java.jvm.deep.chapter04;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * loadClass 双亲委派逻辑
 * findClass 实际类文件读取加载
 */
public class CustomClassLoader extends ClassLoader {
    private String byteCodePath;

    public CustomClassLoader(String byteCodePath) {
        this.byteCodePath = byteCodePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = byteCodePath + name + ".class";
        Class clazz = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(classPath));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            int len;
            byte[] buffer = new byte[1024];
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();
            // 将字节数组转换为Class的实例
            clazz = defineClass(null, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
