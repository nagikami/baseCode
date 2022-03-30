package nagi.java.designpattern.prototype.deepclone;

import java.io.*;

public class Parent implements Serializable, Cloneable {
    private String name = "parent";

    public Child child;

    //自定义clone实现深拷贝
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Parent clone = (Parent) super.clone();
        //对引用类型，进行实例复制（创建新的实例，而不是clone默认的复制引用）
        clone.child = (Child) child.clone();
        return clone;
    }

    //使用序列化实现深拷贝（推荐）
    public Object deepClone() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            //序列化
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            //当前对象以对象流的方式输出
            objectOutputStream.writeObject(this);

            //反序列化
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                byteArrayOutputStream.close();
                objectOutputStream.close();
                byteArrayInputStream.close();
                objectInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
