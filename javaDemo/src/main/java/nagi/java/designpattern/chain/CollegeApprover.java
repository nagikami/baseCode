package nagi.java.designpattern.chain;

public class CollegeApprover extends Approver{

    public CollegeApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPrice() >= 1000) {
            System.out.println(name + " processed");
        } else {
            approver.processRequest(purchaseRequest);
        }
    }
}
