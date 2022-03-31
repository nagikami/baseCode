package nagi.java.jdksrc;

public class FlyWeight {
    public static void main(String[] args) {
        /**
         * valueOf 先判断是否在IntegerCache缓存中，如果不存在则new一个
         * cache默认缓存[-128, 127]
         */
        Integer x = Integer.valueOf(127);
        Integer y = Integer.valueOf(127);
        System.out.println(x == y); //true
        Integer a = new Integer(127);
        Integer b = new Integer(127);
        System.out.println(a == b); //false
        System.out.println(a.equals(b)); //true
    }
}
