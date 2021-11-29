package view.tm;

public class CustomerWiseIncomeTM {
    private String custId;
    private String custName;
    private String address;
    private int noOfOrder;
    private double total;

    public CustomerWiseIncomeTM() {
    }

    public CustomerWiseIncomeTM(String custId, String custName, String address, int noOfOrder, double total) {
        this.custId = custId;
        this.custName = custName;
        this.address = address;
        this.noOfOrder = noOfOrder;
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoOfOrder() {
        return noOfOrder;
    }

    public void setNoOfOrder(int noOfOrder) {
        this.noOfOrder = noOfOrder;
    }
}
