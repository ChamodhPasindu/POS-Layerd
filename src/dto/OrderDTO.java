package dto;


import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String date;
    private String customerId;
    private double cost;
    private ArrayList<OrderDetailDTO> orderDetailDTOS;

    public OrderDTO(String orderId, String date, String customerId, double cost, ArrayList<OrderDetailDTO> orderDetailDTOS) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
        this.cost = cost;
        this.orderDetailDTOS = orderDetailDTOS;
    }

    public OrderDTO() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<OrderDetailDTO> getOrderDetails() {
        return orderDetailDTOS;
    }

    public void setOrderDetails(ArrayList<OrderDetailDTO> orderDetailDTOS) {
        this.orderDetailDTOS = orderDetailDTOS;
    }
}
