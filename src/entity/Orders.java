package entity;

import java.time.LocalDate;

public class Orders {
    private String orderId;
    private LocalDate date;
    private String custId;
    private double cost;

    public Orders() {
    }

    public Orders(String orderId, LocalDate date, String custId, double cost) {
        this.orderId = orderId;
        this.date = date;
        this.custId = custId;
        this.cost = cost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
