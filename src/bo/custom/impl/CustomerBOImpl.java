package bo.custom.impl;

import bo.custom.CustomerBO;
import entity.Customer;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getId(),dto.getTitle(),dto.getName(),dto.getAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public String createCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.createCustomerId();
    }

    @Override
    public CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(s);
        if(c!=null){
            return new CustomerDTO(c.getCustId(),c.getCustTitle(),c.getCustName(),c.getCustAddress(),c.getCity(),c.getProvince(),c.getPostalCode());
        }else {
            return null;
        }
    }


    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerIds();
    }

    @Override
    public ArrayList<String> getCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerId();
    }
}
