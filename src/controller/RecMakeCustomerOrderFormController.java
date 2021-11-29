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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RecMakeCustomerOrderFormController {

    private PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PLACE_ORDER);
    private ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CUSTOMER);
    public AnchorPane subContext;
    public JFXComboBox<String> cmbItemId;
    public JFXComboBox<String> cmbCustomerId;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public Label lblDiscount;
    public JFXTextField txtQty;
    public Label lblCustomerName;
    public Label lblCustomerCity;
    public Label lblCustomerAddress;
    public Label lblDate;
    public TableView<CartTM> tblOrderList;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblTotal;
    public Label lblOrderId;
    public JFXButton btnConfirmOrder;
    public Label lblDescription;


    int selectedRowForRemove=-1;

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        lblTotal.setText("Rs 0.00");
        btnConfirmOrder.setDisable(true);

        loadDate();

        try {
            setOrderId();
            loadItemData();
            loadCustomerData();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if(newValue==null ){
            return;
        }

            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue==null ){
                return;
            }
            try {
                setCustomerData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } );

        tblOrderList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowForRemove= (int) newValue;
        });

    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    private void setOrderId() throws SQLException, ClassNotFoundException {
        lblOrderId.setText(placeOrderBO.CreateOrderId());
    }

    private void setCustomerData(String newValue) throws SQLException, ClassNotFoundException {
        CustomerDTO customers = customerBO.searchCustomer(newValue);
        if(customers==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
        }else {
            lblCustomerName.setText(customers.getName());
            lblCustomerAddress.setText(customers.getAddress());
            lblCustomerCity.setText(customers.getCity());
        }
    }

    public void loadCustomerData() throws SQLException, ClassNotFoundException {
        ArrayList<String> customers = customerBO.getAllCustomerIds();
        cmbCustomerId.getItems().addAll(customers);

    }

    String description;
    private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = itemBO.searchItem(newValue);
        if(itemDTO ==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
        }else{
            description= itemDTO.getDescription();
            lblQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
            lblUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
            lblDiscount.setText(String.valueOf(itemDTO.getDiscount()));
            lblDescription.setText(itemDTO.getDescription());

        }
    }

    private void loadItemData() throws SQLException, ClassNotFoundException {
        ArrayList<String> items = itemBO.getItemIds();
        cmbItemId.getItems().addAll(items);
    }

    public void openAddCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add new Customer");
        stage.show();
        stage.setOnCloseRequest(event ->{
            try {
                cmbCustomerId.getItems().clear();
                loadCustomerData();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } );
    }

    ObservableList<CartTM> cartTMObservableList=FXCollections.observableArrayList();
    public void addToListOnAction(ActionEvent actionEvent) {

        if(txtQty.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Enter qty").show();
            return;
        }
        btnConfirmOrder.setDisable(false);

        String itemCode=cmbItemId.getValue();
        int qty=Integer.parseInt(txtQty.getText());
        double unitPrice=Double.parseDouble(lblUnitPrice.getText());
        double discount=Double.parseDouble(lblDiscount.getText());
        double total=unitPrice*qty-(unitPrice*qty*discount/100);

        if(Integer.parseInt(lblQtyOnHand.getText())<qty){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
            return;
        }

        CartTM cartTM=new CartTM(
                itemCode,description,qty,unitPrice,discount,total
        );

        int rowNumber=isExists(cartTM);
        if(rowNumber==-1){
            cartTMObservableList.add(cartTM);

        }else{
            CartTM temp=cartTMObservableList.get(rowNumber);
            CartTM newCartTM=new CartTM(
                    itemCode,description,qty+temp.getQty(),unitPrice,discount,total+temp.getTotal()
            );

            if(newCartTM.getQty()>Integer.parseInt(lblQtyOnHand.getText())){
                System.out.println(newCartTM.getTotal());
                new Alert(Alert.AlertType.WARNING,"QTY out of range").show();
                return;
            }
            cartTMObservableList.remove(rowNumber);
            cartTMObservableList.add(newCartTM);
        }
        tblOrderList.setItems(cartTMObservableList);
        calculateTotal();
    }

    private int isExists(CartTM cartTM) {
        for (int i=0;i<cartTMObservableList.size();i++){
            if(cartTM.getItemCode().equals(cartTMObservableList.get(i).getItemCode())){
                return i;
            }
        }
        return -1;
    }

    public void removeSelectedItemOnAction(ActionEvent actionEvent) {
        if(selectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please select a row").show();
        }else{
            cartTMObservableList.remove(selectedRowForRemove);
            if(cartTMObservableList.isEmpty()){
                btnConfirmOrder.setDisable(true);
            }
            calculateTotal();
            tblOrderList.refresh();
        }
    }

    private void calculateTotal(){
        double total=0.00;
        for (CartTM tm:cartTMObservableList) {
            total=total+tm.getTotal();
        }
        lblTotal.setText("Rs "+String.valueOf(total));
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {

        if(cmbCustomerId.getValue()==null){
            new Alert(Alert.AlertType.WARNING,"Please Select customer").show();
            return;
        }

        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        double total=0;

        for (CartTM tm : cartTMObservableList) {
            total+=tm.getTotal();
            orderDetailDTOS.add(new OrderDetailDTO(
                    lblOrderId.getText(),tm.getItemCode(),tm.getQty(),tm.getDiscount(),tm.getTotal())
            );
        }

        OrderDTO orderDTO = new OrderDTO(
                lblOrderId.getText(),
                lblDate.getText(),
                cmbCustomerId.getValue(),
                total,
                orderDetailDTOS

        );

        if (placeOrderBO.placeOrder(orderDTO)) {
            new Alert(Alert.AlertType.INFORMATION,"Successful").show();

            HashMap hashMap = new HashMap();
            hashMap.put("orderId",lblOrderId.getText());
            hashMap.put("total",lblTotal.getText());

            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/orderBill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hashMap , new JRBeanArrayDataSource(cartTMObservableList.toArray()));
            JasperViewer.viewReport(jasperPrint,false);


        }else{
            new Alert(Alert.AlertType.INFORMATION,"Try Again").show();
        }
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) {
        tblOrderList.getItems().removeAll(cartTMObservableList);
        tblOrderList.refresh();
        calculateTotal();
    }

    public void clearAllOnAction(ActionEvent actionEvent) {
        cmbItemId.setValue(null);
        cmbCustomerId.setValue(null);
        txtQty.clear();
        lblDescription.setText("");
        lblQtyOnHand.setText("");
        lblCustomerCity.setText("");
        lblUnitPrice.setText("");
        lblCustomerAddress.setText("");
        lblDiscount.setText("");
        lblCustomerName.setText("");
    }


}
