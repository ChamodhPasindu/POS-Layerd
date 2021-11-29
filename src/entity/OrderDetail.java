package entity;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int orderQty;
    private double discount;
    private double cost;

    public OrderDetail() {
    }

    public OrderDetail(String itemCode, int orderQty, double discount, double cost) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.cost = cost;
    }

    public OrderDetail(String orderId, String itemCode, int orderQty, double discount, double cost) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.cost = cost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
