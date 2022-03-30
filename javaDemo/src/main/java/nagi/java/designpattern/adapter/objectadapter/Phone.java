package nagi.java.designpattern.adapter.objectadapter;

public class Phone {
    public void charge(IVoltage5 iVoltage5) {
        if (iVoltage5.output5() == 5) {
            System.out.println("charge success");
        } else {
            System.out.println("charge failed");
        }
    }
}
