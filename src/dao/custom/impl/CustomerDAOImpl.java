package dao.custom.impl;

import entity.Customer;
import dao.CrudUtil;
import dao.custom.CustomerDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public String createCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT CustId FROM customer ORDER BY CustId DESC LIMIT 1");
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "C-00"+tempId;
            }else if(tempId<=99){
                return "C-0"+tempId;
            }else{
                return "C-"+tempId;
            }

        }else{
            return "C-001";
        }
    }

    @Override
    public ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<String> customers = new ArrayList();
        while (rst.next()){
            customers.add(
                    rst.getString(1)
            );

        }
        return customers;
    }

    @Override
    public ArrayList<String> getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT DISTINCT CustId FROM Orders ");
        ArrayList<String> customerId = new ArrayList<>();
        while (rst.next()){
            customerId.add(rst.getString(1));
        }
        return customerId;
    }

    @Override
    public boolean add(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUE (?,?,?,?,?,?,?)",c.getCustId(),c.getCustTitle(),c.getCustName(),
                c.getCustAddress(),c.getCity(),c.getProvince(),c.getPostalCode());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE CustId =? ", s);
        if (rst.next()){
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }


}
