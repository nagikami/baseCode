package nagi.java.designpattern.decorator;

//装饰者基类
public class Decorator extends Component {

    //组合被装饰者组件
    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public double cost() {
        return price + component.cost();
    }
}
