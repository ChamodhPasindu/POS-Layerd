package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.PlaceOrderBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.CustomerDTO;
import view.tm.CustomerWiseIncomeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminIncomeReportsController {

    private PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PLACE_ORDER);
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CUSTOMER);

    public TableView tblCustomerList;
    public TableColumn colCustId;
    public TableColumn colCustName;
    public TableColumn colAddress;
    public TableColumn colNoOfOrders;
    public TableColumn colPayment;
    public DatePicker txtForm;
    public DatePicker txtTo;
    public Label lblTotalIncome;
    public Label lblNoOfOrders;

    public void initialize() {


        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNoOfOrders.setCellValueFactory(new PropertyValueFactory<>("noOfOrder"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("total"));


        try {
            loadCustomerWiseIncomeDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        txtForm.valueProperty().addListener((observable, oldValue, newValue1) -> {
            txtTo.valueProperty().addListener((observable1, oldValue1, newValue2) -> {
                int total=0;
                try {
                    ArrayList<String> orderId = placeOrderBO.getDate(String.valueOf(newValue1), String.valueOf(newValue2));
                    lblNoOfOrders.setText(String.valueOf(orderId.size()));

                    for (String oId:orderId) {
                        total+= placeOrderBO.getSumOfTotal(oId);
                    }

                    lblTotalIncome.setText("Rs : "+String.valueOf(total));

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            });

        });
    }

    ObservableList<CustomerWiseIncomeTM> incomeTMS = FXCollections.observableArrayList();
    private void loadCustomerWiseIncomeDetails() throws SQLException, ClassNotFoundException {
        double total=0.0;
        ArrayList<String> customerId =customerBO.getCustomerId();

        for (String custId:customerId) {
            ArrayList<String> orderId = placeOrderBO.getOrderId(custId);
            CustomerDTO customer = customerBO.searchCustomer(custId);

            for (String oId:orderId) {
                total+= placeOrderBO.getSumOfTotal(oId);
            }

            incomeTMS.add(new CustomerWiseIncomeTM(custId,customer.getName(),customer.getAddress(),orderId.size(),total));
            total=0.0;
        }
        tblCustomerList.setItems(incomeTMS);
    }
}
