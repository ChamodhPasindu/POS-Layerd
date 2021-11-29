package dao.custom.impl;

import entity.Item;
import dao.CrudUtil;
import dao.custom.ItemDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public String CreateItemId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1");
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "I-00"+tempId;
            }else if(tempId<=99){
                return "I-0"+tempId;
            }else{
                return "I-"+tempId;
            }

        }else{
            return "I-001";
        }
    }

    @Override
    public ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<String> items = new ArrayList();
        while (rst.next()){
            items.add(
                    rst.getString(1)
            );
        }
        return items;
    }

    @Override
    public ArrayList<Item> getAllItemDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE QtyOnHand !=0");
        ArrayList<Item> itemList =new ArrayList();
        while (rst.next()){
            itemList.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            ));
        }
        return itemList;
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET QtyOnHand=(QtyOnHand-" + qty
                + ") WHERE ItemCode='" + itemCode + "'");
    }

    @Override
    public void updateQtyFromManageOrder(String itemCode, int qty) throws SQLException, ClassNotFoundException {
         CrudUtil.executeUpdate("UPDATE Item SET QtyOnHand=(QtyOnHand+" + qty
                + ") WHERE ItemCode='" + itemCode + "'");
    }

    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("INSERT INTO  Item VALUE (?,?,?,?,?,?)",dto.getItemCode(),dto.getDescription(),dto.getPackSize(),
                dto.getUnitPrice(),dto.getQtyOnHand(),dto.getDiscount());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE ItemCode = ?",s);
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate( "UPDATE Item set Description = ? , PackSize = ? , UnitPrice = ? , QtyOnHand = ? , Discount = ? WHERE ItemCode = ?",
                dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getDiscount(),dto.getItemCode());

    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode = ?", s);
        if(rst.next()){
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> itemList = new ArrayList<>();
        while (rst.next()){
            itemList.add(new Item(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)));
        }
        return itemList;
    }
}
