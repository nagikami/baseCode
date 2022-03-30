package nagi.java.designpattern.builder;

//构造器指挥者，聚合构造器，编排构造逻辑
public class BuilderDirector {
    private AbstractBuilder abstractBuilder;

    public BuilderDirector(AbstractBuilder abstractBuilder) {
        this.abstractBuilder = abstractBuilder;
    }

    //构造逻辑
    public House buildHouse() {
        abstractBuilder.crateBasic();
        abstractBuilder.createWall();
        return abstractBuilder.buildHouse();
    }

}
