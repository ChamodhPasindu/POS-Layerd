package bo.custom.impl;

import bo.custom.ItemBO;
import entity.Item;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public String CreateItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.CreateItemId();
    }

    @Override
    public ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemIds();
    }

    @Override
    public ArrayList<ItemDTO> getAllItemDetails() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItemDetails = itemDAO.getAllItemDetails();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item i : allItemDetails) {
            itemDTOS.add(new ItemDTO(i.getItemCode(),i.getDescription(),i.getPackSize(),i.getUnitPrice(),i.getQtyOnHand(),i.getDiscount()));
        }
        return itemDTOS;
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQty(itemCode,qty);
    }

    @Override
    public void updateQtyFromManageOrder(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        itemDAO.updateQtyFromManageOrder(itemCode,qty);
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getItemCOde(),dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand(),
                dto.getDiscount()));
    }

    @Override
    public boolean deleteItem(String s) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(s);
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItemCOde(),dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand(),
                dto.getDiscount()));
    }

    @Override
    public ItemDTO searchItem(String s) throws SQLException, ClassNotFoundException {
        Item item= itemDAO.search(s);
        if(item!=null){
            return new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand(),item.getDiscount());
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO>itemDTOS = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            itemDTOS.add(new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),
                    item.getQtyOnHand(),item.getDiscount()));
        }
        return itemDTOS;

    }
}
