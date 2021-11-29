package dao.custom.impl;

import entity.OrderDetail;
import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import dto.CustomDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean add(OrderDetail dto) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("INSERT INTO OrderDetail VALUES(?,?,?,?,?)",dto.getOrderId(),dto.getItemCode(),dto.getOrderQty(),
                dto.getDiscount(),dto.getCost());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getOrderDetail(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode,OrderQty,Discount,Cost FROM OrderDetail WHERE OrderId=?", id);
        ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
        while (rst.next()){
            orderDetailList.add(new OrderDetail(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getDouble(3),
                    rst.getDouble(4)
            ));
        }
        return orderDetailList;
    }

    @Override
    public boolean removeItemFromOrder(String id) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("DELETE FROM OrderDetail WHERE ItemCode=?",id);
    }

    @Override
    public ArrayList<CustomDTO> findMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode,count(*) FROM OrderDetail GROUP BY ItemCode ORDER BY 1");

        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        while (rst.next()){
            customDTOS.add(new CustomDTO(
                    rst.getString(1),
                    rst.getInt(2)
            ));
        }
        return customDTOS;
    }

    @Override
    public double getSumOfTotal(String id) throws SQLException, ClassNotFoundException {
        double i=0.0;
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(Cost) Total FROM OrderDetail WHERE OrderId=?", id);
        if(rst.next()){
            i=rst.getDouble(1);
        }
        return i;
    }
}
