package dto;

public class ItemDTO {
    private String itemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qtyOnHand;
    private double discount;



    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String description) {
        this.itemCode = itemCode;
        this.description = description;
    }

    public ItemDTO(String itemCode, String description, String packSize, double unitPrice, int qtyOnHand , double discount) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;


    }


    public void setDiscount(){
        this.discount = discount;
    }

    public double getDiscount(){
        return discount;
    }

    public String getItemCOde() {
        return itemCode;
    }

    public void setItemCOde(String itemCOde) {
        this.itemCode = itemCOde;
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
}
