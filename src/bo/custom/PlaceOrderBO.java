package bo.custom;

import bo.SuperBO;
import javafx.collections.ObservableList;
import dto.CustomDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    String CreateOrderId() throws SQLException, ClassNotFoundException;

    boolean placeOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    void updateOrder(String id, ObservableList<CartTM> cartTMArrayList) throws SQLException, ClassNotFoundException;

    ArrayList<String> getOrderId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getDate(String from, String to) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String s) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException;

    boolean removeItemFromOrder(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> findMovableItem() throws SQLException, ClassNotFoundException;

    double getSumOfTotal(String id) throws SQLException, ClassNotFoundException;



}
