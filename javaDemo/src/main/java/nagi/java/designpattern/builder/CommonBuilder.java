package nagi.java.designpattern.builder;

//具体的构造器
public class CommonBuilder extends AbstractBuilder{
    @Override
    public void crateBasic() {
        System.out.println("common basic");
    }

    @Override
    public void createWall() {
        System.out.println("common wall");
        house.setName("common");
    }
}
