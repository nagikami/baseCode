package nagi.java.designpattern.chain;

public class DepartmentApprover extends Approver{
    public DepartmentApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPrice() < 1000) {
            System.out.println(name + " processed");
        } else {
            //转交下一个处理者
            approver.processRequest(purchaseRequest);
        }
    }
}
