package nagi.java.rmi;

import java.io.Serializable;

/**
 * RMI数据传输对象，因为数据通过序列化的二进制流传输，所以需要实现Serializable接口
 */
public class User implements Serializable {
    // 客户端和服务端的序列化id需要保持一致，保整se/de的对称性
    public static final long serialVersionUID = 1L;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
