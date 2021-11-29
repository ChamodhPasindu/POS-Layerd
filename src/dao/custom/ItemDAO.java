package dao.custom;

import entity.Item;
import dao.CrudDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    String CreateItemId() throws SQLException, ClassNotFoundException;

    ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException;

    ArrayList<Item> getAllItemDetails() throws SQLException, ClassNotFoundException;

    boolean updateQty(String itemCode,int qty) throws SQLException, ClassNotFoundException;

    void updateQtyFromManageOrder(String itemCode,int qty) throws SQLException, ClassNotFoundException;

}
