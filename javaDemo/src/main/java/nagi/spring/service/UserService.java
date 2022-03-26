package nagi.spring.service;

import nagi.spring.spring.*;

@Component("userService")
@Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean, UserInterface {
    @AutoWired
    private OrderService orderService;

    private String beanName;

    //由spring调用
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void test() {
        System.out.println(orderService);
        System.out.println(beanName);
    }

    //初始化方法，spring创建bean完成属性注入后回调
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Do something after properties set");
    }
}
