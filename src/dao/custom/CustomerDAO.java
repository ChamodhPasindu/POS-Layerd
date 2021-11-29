package dao.custom;

import entity.Customer;
import dao.CrudDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String createCustomerId() throws SQLException, ClassNotFoundException;

    ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    ArrayList<String> getCustomerId() throws SQLException, ClassNotFoundException;


}
