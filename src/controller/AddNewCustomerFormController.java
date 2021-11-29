package controller;

import com.jfoenix.controls.JFXTextField;
import bo.BOFactory;
import bo.custom.CustomerBO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import dto.CustomerDTO;
import validation.Validate;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddNewCustomerFormController {

    private CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CUSTOMER);
    public JFXTextField txtId;
    public JFXTextField txtTitle;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXTextField txtName;

    public void initialize(){
        try {
            setCustomerId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerId() throws SQLException, ClassNotFoundException {
        txtId.setText(customerBO.createCustomerId());
    }

    public void confirmCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO customerDTO = new CustomerDTO(txtId.getText(),txtTitle.getText(),txtName.getText(),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText());

        ArrayList<JFXTextField> fields = new ArrayList<>();
        fields.add(txtTitle);
        fields.add(txtName);
        fields.add(txtAddress);
        fields.add(txtCity);
        fields.add(txtProvince);
        fields.add(txtPostalCode);

        ArrayList<String> regex = new ArrayList<>();
        regex.add("^(Mr|Ms|Dr|Sr)$");
        regex.add("^[A-z\\.\\s]{3,50}$");
        regex.add("^[A-z0-9\\,\\/\\-\\s\\.]{5,}$");
        regex.add("^[A-z]{5,}$");
        regex.add("^[A-z]{5,}$");
        regex.add("^[0-9]{5,}$");

        Object validate = Validate.validate(fields, regex);
        if (validate instanceof String){
            new Alert(Alert.AlertType.WARNING,"Invalid Value \""+validate+"\"").show();
            return;
        }else if(validate==null){
            new Alert(Alert.AlertType.WARNING,"Fill all the TextFields").show();
            return;
        }

        if(customerBO.addCustomer(customerDTO)){
            new Alert(Alert.AlertType.INFORMATION,"Customer added Successfully ").show();
            clearAllOnAction();

        }else{
            new Alert(Alert.AlertType.WARNING,"Error.Try Again latter").show();
        }
    }

    public void clearAllOnAction() {
        txtAddress.clear();
        txtId.clear();
        txtCity.clear();
        txtName.clear();
        txtPostalCode.clear();
        txtProvince.clear();
        txtTitle.clear();
    }
}
