package nagi.java.designpattern.builder;

public class Test {
    public static void main(String[] args) {
        //实例化构造器
        CommonBuilder commonBuilder = new CommonBuilder();
        //实例化指挥者
        BuilderDirector builderDirector = new BuilderDirector(commonBuilder);
        System.out.println(builderDirector.buildHouse().getName());
    }
}
