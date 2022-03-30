package nagi.java.designpattern.adapter.objectadapter;

/**
 * 对象适配器模式 (常用)
 * 通过持有src类实例，实现dst接口完成src->dst的转换 用组合替代继承
 */
public class VoltageAdapter implements IVoltage5 {

    private Voltage220 voltage220;

    public VoltageAdapter(Voltage220 voltage220) {
        this.voltage220 = voltage220;
    }

    @Override
    public int output5() {
        int src = voltage220.output220();
        //接口转换
        int dst = src / 44;
        return dst;
    }
}
