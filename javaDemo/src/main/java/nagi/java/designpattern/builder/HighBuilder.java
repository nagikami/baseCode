package nagi.java.designpattern.builder;

public class HighBuilder extends AbstractBuilder {
    @Override
    public void crateBasic() {
        System.out.println("high basic");
    }

    @Override
    public void createWall() {
        System.out.println("high wall");
        house.setName("High");
    }
}
