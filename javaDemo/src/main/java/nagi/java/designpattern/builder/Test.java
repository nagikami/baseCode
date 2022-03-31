package nagi.java.designpattern.builder;

/**
 * 构造模式
 * 指挥者聚合实现了抽象构造器的具体构造器，编排构造流程
 */
public class Test {
    public static void main(String[] args) {
        //实例化构造器
        CommonBuilder commonBuilder = new CommonBuilder();
        //实例化指挥者
        BuilderDirector builderDirector = new BuilderDirector(commonBuilder);
        System.out.println(builderDirector.buildHouse().getName());
    }
}
