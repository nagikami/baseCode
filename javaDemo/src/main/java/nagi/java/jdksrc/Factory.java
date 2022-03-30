package nagi.java.jdksrc;

import java.util.Calendar;

public class Factory {
    public static void main(String[] args) {
        //简单工厂模式
        Calendar calendar = Calendar.getInstance();
        System.out.println("Year:" + calendar.get(Calendar.YEAR));
        Runtime.getRuntime();
    }
}
