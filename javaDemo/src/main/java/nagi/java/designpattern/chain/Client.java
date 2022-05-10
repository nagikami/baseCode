package nagi.java.designpattern.chain;

/**
 * 职责链模式
 * Handler 抽象处理者，定义一个处理请求的接口，同时包含另外的handler
 * ConcreteHandler 具体的处理者，处理自己负责的请求，可以访问后继者，若可以处理当前请求则处理，否则交给后继者，形成职责链
 * Request 含有多个属性，表示一个请求
 * SpringMVC 的dispatcher方法调用的ExecuteHandlerChain中包含的拦截器链使用职责链模式
 */
public class Client {
    public static void main(String[] args) {
        DepartmentApprover tom = new DepartmentApprover("Tom");
        CollegeApprover john = new CollegeApprover("John");
        //形成环路，方便从任意处理者开始
        tom.setApprover(john);
        john.setApprover(tom);

        tom.processRequest(new PurchaseRequest(1, 10000, 1));
    }
}
