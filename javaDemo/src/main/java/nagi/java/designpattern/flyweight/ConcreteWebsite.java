package nagi.java.designpattern.flyweight;

//具体享元角色
public class ConcreteWebsite extends Website {
    //内部状态
    private String type;

    public ConcreteWebsite(String type) {
        this.type = type;
    }

    //user为外部状态
    @Override
    void use(User user) {
        System.out.println("website is " + type + " usr is " + user.getName());
    }
}
