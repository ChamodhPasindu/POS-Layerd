package dao.custom.impl;

import entity.Orders;
import dao.CrudUtil;
import dao.custom.OrderDAO;
import javafx.collections.ObservableList;
import view.tm.CartTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String CreateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1");
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<=99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }

    @Override
    public void updateOrder(String id, ObservableList<CartTM> cartTMArrayList) throws SQLException, ClassNotFoundException {
        CrudUtil.executeUpdate("DELETE FROM OrderDetail WHERE OrderId =?",id);
        for (CartTM tm:cartTMArrayList) {
            CrudUtil.executeUpdate("INSERT INTO OrderDetail VALUES(?,?,?,?,?)",id,tm.getItemCode(),tm.getQty(),tm.getDiscount(),
                    tm.getTotal());
        }
    }

    @Override
    public ArrayList<String> getOrderId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM Orders WHERE CustId=?", id);
        ArrayList<String> orderId=new ArrayList<>();
        while (rst.next()){
            orderId.add(rst.getString(1));
        }
        return orderId;
    }

    @Override
    public ArrayList<String> getDate(String from, String to) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM Orders WHERE orderDate BETWEEN ? AND ?", from, to);
        ArrayList<String> orderList=new ArrayList<>();
        while (rst.next()) {
            orderList.add(rst.getString(1));
        }
        return orderList;
    }

    @Override
    public boolean add(Orders dto) throws SQLException, ClassNotFoundException {
      return CrudUtil.executeUpdate("INSERT INTO Orders VALUES(?,?,?,?)",dto.getOrderId(),dto.getDate(),dto.getCustId(),dto.getCost());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("DELETE FROM Orders WHERE OrderId=?",s);
    }

    @Override
    public boolean update(Orders DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
