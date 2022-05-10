package nagi.java.designpattern.chain;

/**
 * 抽象Handler
 */
public abstract class Approver {
    //下一个处理者
    Approver approver;
    String name;

    public Approver(String name) {
        this.name = name;
    }

    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    //请求处理方法，由子类实现
    public abstract void processRequest(PurchaseRequest purchaseRequest);
}
