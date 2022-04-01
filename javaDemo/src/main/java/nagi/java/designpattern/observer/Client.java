package nagi.java.designpattern.observer;

/**
 * 观察者模式
 * Subject 数据持有者接口
 * Observer 观察者接口
 */
public class Client {
    public static void main(String[] args) {
        MySubject mySubject = new MySubject();
        MyObserver myObserver = new MyObserver();
        mySubject.registerObserver(myObserver);
        mySubject.updateName("nagi");
        mySubject.removeObserver(myObserver);
        mySubject.updateName("hello");
    }
}
