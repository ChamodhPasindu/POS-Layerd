package bo.custom.impl;

import bo.custom.PlaceOrderBO;
import entity.OrderDetail;
import entity.Orders;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DbConnection;
import javafx.collections.ObservableList;
import dto.CustomDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import view.tm.CartTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public String CreateOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.CreateOrderId();
    }

    @Override
    public boolean placeOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        Connection con=null;

        con=DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);


        Orders order =new Orders(dto.getOrderId(),LocalDate.parse(dto.getDate()),dto.getCustomerId(),dto.getCost());
        boolean addOrder = orderDAO.add(order);
        if (!addOrder){
            con.rollback();
            con.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO od : dto.getOrderDetails()){
            OrderDetail orderDetail = new OrderDetail(od.getOrderId(),od.getItemCode(),od.getOrderQty(),od.getDiscount(),od.getTotal());
            boolean addOrderDetail = orderDetailsDAO.add(orderDetail);
            if(!addOrderDetail){
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }

            boolean updateItem = itemDAO.updateQty(od.getItemCode(), od.getOrderQty());
            if (!updateItem){
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }
        }
        con.commit();
        con.setAutoCommit(true);
        return true;

    }

    @Override
    public void updateOrder(String id, ObservableList<CartTM> cartTMArrayList) throws SQLException, ClassNotFoundException {
        orderDAO.updateOrder(id, cartTMArrayList);
    }

    @Override
    public ArrayList<String> getOrderId(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderId(id);
    }

    @Override
    public ArrayList<String> getDate(String from, String to) throws SQLException, ClassNotFoundException {
        return orderDAO.getDate(from,to);
    }

    @Override
    public boolean deleteOrder(String s) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(s);
    }

    @Override
    public ArrayList<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetail = orderDetailsDAO.getOrderDetail(id);
        ArrayList<OrderDetailDTO> detailDTOS = new ArrayList<>();

        for (OrderDetail od : orderDetail) {
            detailDTOS.add(new OrderDetailDTO(od.getItemCode(),od.getOrderQty(),od.getDiscount(),od.getCost()));
        }
        return detailDTOS;
    }

    @Override
    public boolean removeItemFromOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.removeItemFromOrder(id);
    }

    @Override
    public ArrayList<CustomDTO> findMovableItem() throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.findMovableItem();
    }

    @Override
    public double getSumOfTotal(String id) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.getSumOfTotal(id);
    }
}
