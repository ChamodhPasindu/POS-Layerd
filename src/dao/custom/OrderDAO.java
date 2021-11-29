package dao.custom;

import entity.Orders;
import dao.CrudDAO;
import javafx.collections.ObservableList;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Orders,String> {

    String CreateOrderId() throws SQLException, ClassNotFoundException;

    void updateOrder(String id, ObservableList<CartTM> cartTMArrayList) throws SQLException, ClassNotFoundException;

    ArrayList<String> getOrderId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getDate(String from, String to) throws SQLException, ClassNotFoundException;
}
