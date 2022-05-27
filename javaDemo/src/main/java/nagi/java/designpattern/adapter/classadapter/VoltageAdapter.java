package nagi.java.designpattern.adapter.classadapter;

/**
 * 类适配器模式
 * 通过继承src类，实现dst接口完成src->dst的转换
 * 缺点 需要继承src dst必须是接口 src方法都会暴露在adapter
 * 优点 可以重写src方法，增加灵活性
 */
public class VoltageAdapter extends Voltage220 implements IVoltage5 {

    @Override
    public int output5() {
        int src = output220();
        //接口转换
        int dst = src / 44;
        return dst;
    }
}
