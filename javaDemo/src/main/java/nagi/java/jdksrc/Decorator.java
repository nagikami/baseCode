package nagi.java.jdksrc;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class Decorator {
    public static void main(String[] args) throws Exception {
        /**
         *InputStream = Component 组件抽象类
         *FileInputStream被装饰者组件
         *FilterInputStream装饰者基类
         * DataInputStream装饰者
         */
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./aa.txt"));
    }
}
