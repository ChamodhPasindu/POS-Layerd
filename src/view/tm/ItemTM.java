package view.tm;

public class ItemTM {
    private String itemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qtyOnHand;
    private double discount;

    public ItemTM() {
    }

    public ItemTM(String itemCode, String description, int qtyOnHand) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
    }

    public ItemTM(String itemCode, String description) {
        this.itemCode = itemCode;
        this.description = description;
    }

    public ItemTM(String itemCode, String description, double unitPrice, int qtyOnHand) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public ItemTM(String itemCode, String description, String packSize, double unitPrice, int qtyOnHand, double discount) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
