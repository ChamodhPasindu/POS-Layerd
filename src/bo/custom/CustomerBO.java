package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    public String createCustomerId() throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;

    ArrayList<String> getCustomerId() throws SQLException, ClassNotFoundException;
}
