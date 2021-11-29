package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.PlaceOrderBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;
import dto.OrderDetailDTO;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RecManageOrderFormController {

    private PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PLACE_ORDER);
    private ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CUSTOMER);

    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbOrderList;
    public TableView tblItemDetails;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colOrderQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblTotal;
    public JFXButton btnConfirmOrderEdit;
    public JFXTextField txtNewQty;
    public Label lblCurrentQty;
    public Label lblNormal;
    public JFXButton btnOk;
    public Label lblAdditional;

    int selectedRowForRemove=-1;


    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        btnConfirmOrderEdit.setDisable(true);
        txtNewQty.setDisable(true);
        lblCurrentQty.setDisable(true);
        lblNormal.setDisable(true);
        btnOk.setDisable(true);

        lblTotal.setText("0.00");
        lblAdditional.setText("0.00");


        try {
            loadCustomerId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadOrderId(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbOrderList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           cartTMObservableList.clear();
            try {
                loadItemDetails(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            calculateTotal();
        });

        tblItemDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowForRemove= (int) newValue;
        });
    }

    ObservableList<CartTM> cartTMObservableList= FXCollections.observableArrayList();
    private void loadItemDetails(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> orderDetailDTO = placeOrderBO.getOrderDetail(newValue);
        ArrayList<ItemDTO> itemDTOList =new ArrayList<>();
        for (OrderDetailDTO od: orderDetailDTO) {
            itemDTOList.add(itemBO.searchItem(od.getItemCode()));
        }

       for(int i = 0; i< orderDetailDTO.size(); i++){

           cartTMObservableList.add(new CartTM(orderDetailDTO.get(i).getItemCode(),
                   itemDTOList.get(i).getDescription(),
                   orderDetailDTO.get(i).getOrderQty(),
                   itemDTOList.get(i).getUnitPrice(),
                   orderDetailDTO.get(i).getDiscount(),
                   orderDetailDTO.get(i).getTotal()
           ));
        }
       tblItemDetails.setItems(cartTMObservableList);
       btnConfirmOrderEdit.setDisable(false);

    }

    private void loadOrderId(String newValue) throws SQLException, ClassNotFoundException {
        cmbOrderList.getItems().clear();
        ArrayList<String> orderId = placeOrderBO.getOrderId(newValue);
        cmbOrderList.getItems().addAll(orderId);
    }

    private void loadCustomerId() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerId = customerBO.getCustomerId();
        cmbCustomerId.getItems().addAll(customerId);
    }

    public void removeItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(selectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please select a row").show();
        }else{

            itemBO.updateQtyFromManageOrder(cartTMObservableList.get(selectedRowForRemove).getItemCode(),
                    cartTMObservableList.get(selectedRowForRemove).getQty());

            placeOrderBO.removeItemFromOrder(cartTMObservableList.get(selectedRowForRemove).getItemCode());


            cartTMObservableList.remove(selectedRowForRemove);

            if(cartTMObservableList.isEmpty()){
                btnConfirmOrderEdit.setDisable(true);
            }
            calculateTotal();
            tblItemDetails.refresh();
        }
    }

    private void calculateTotal() {
        double total=0.00;
        for (CartTM tm:cartTMObservableList) {
            total=total+tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                , "Do you want to Delete this Order?", yes, no);
        alert.setTitle("Delete Confirmation");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {

            for (CartTM tm:cartTMObservableList) {
                itemBO.updateQtyFromManageOrder(tm.getItemCode(),tm.getQty());
            }

           placeOrderBO.deleteOrder(cmbOrderList.getValue());
            cmbOrderList.getItems().clear();
            cmbCustomerId.getItems().clear();
            initialize();

        } else {

        }

    }

    public void confirmOrderEditsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(cartTMObservableList.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Already items remove in this order,now cancel this Order").show();
        }else{
           placeOrderBO.updateOrder(cmbOrderList.getValue(),cartTMObservableList);
            new Alert(Alert.AlertType.INFORMATION,"Successfully").show();
            cmbOrderList.getItems().clear();
            cmbCustomerId.getItems().clear();
            initialize();
        }
    }

    public void editOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(selectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please select a row").show();
            return;
        }
        txtNewQty.setDisable(false);
        lblCurrentQty.setDisable(false);
        lblNormal.setDisable(false);
        btnOk.setDisable(false);

        ItemDTO itemDTO = itemBO.searchItem(cartTMObservableList.get(selectedRowForRemove).getItemCode());

        lblCurrentQty.setText(String.valueOf(itemDTO.getQtyOnHand()));


    }

    public void btnOkOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if(txtNewQty.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Enter New Value").show();
            return;
        }
        if(Integer.parseInt(txtNewQty.getText())>Integer.parseInt(lblCurrentQty.getText())){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
            return;
        }
        cartTMObservableList.get(selectedRowForRemove).setQty(cartTMObservableList.get(selectedRowForRemove).getQty() + Integer.parseInt(txtNewQty.getText()));
        cartTMObservableList.get(selectedRowForRemove).setTotal(cartTMObservableList.get(selectedRowForRemove).getQty() *
                cartTMObservableList.get(selectedRowForRemove).getUnitPrice() -
                cartTMObservableList.get(selectedRowForRemove).getUnitPrice() *
                cartTMObservableList.get(selectedRowForRemove).getQty() *
                cartTMObservableList.get(selectedRowForRemove).getDiscount()/100);

        tblItemDetails.refresh();
        double total=Double.parseDouble(lblTotal.getText());

        calculateTotal();

        total=Double.parseDouble(lblTotal.getText())-total;
        lblAdditional.setText(String.valueOf(total));

        itemBO.updateQty(cartTMObservableList.get(selectedRowForRemove).getItemCode(),Integer.parseInt(txtNewQty.getText()));
        editOrderOnAction(actionEvent);
    }
}
