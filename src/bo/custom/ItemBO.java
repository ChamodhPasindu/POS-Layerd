package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    String CreateItemId() throws SQLException, ClassNotFoundException;

    ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItemDetails() throws SQLException, ClassNotFoundException;

    boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException;

    void updateQtyFromManageOrder(String itemCode, int qty) throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String s) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String s) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
}
