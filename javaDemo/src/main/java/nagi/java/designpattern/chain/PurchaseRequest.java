package nagi.java.designpattern.chain;

/**
 * 请求类
 */
public class PurchaseRequest {
    private int type = 0;
    private float price = 0.0f;
    private int id = 0;

    public PurchaseRequest(int type, float price, int id) {
        this.type = type;
        this.price = price;
        this.id = id;
    }

    public float getPrice() {
        return price;
    }
}
