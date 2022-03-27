package nagi.java.dynamicproxy;

public class UserDaoImpl implements UserDao {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        System.out.println("b invoke");
        add(a, b);
        System.out.println("a invoke");
        return a * b;
    }
}
