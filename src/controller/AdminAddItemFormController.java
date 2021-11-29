package controller;

import com.jfoenix.controls.JFXTextField;
import bo.BOFactory;
import bo.custom.ItemBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;
import validation.Validate;
import view.tm.ItemTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminAddItemFormController {

    private ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);


    public TableView tblItems;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;

    public JFXTextField txtItemCode;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQonHand;
    public JFXTextField txtDescription;
    public JFXTextField txtDiscount;

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        try {
            setItemId();
            loadItemTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<ItemTM>observableList = FXCollections.observableArrayList();
    private void loadItemTable() throws SQLException, ClassNotFoundException {
        observableList.clear();

        ArrayList<ItemDTO> allItemDetails = itemBO.getAllItemDetails();
        for (ItemDTO dto : allItemDetails) {
            observableList.add(new ItemTM(dto.getItemCOde(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
        }
        tblItems.setItems(observableList);
    }

    private void setItemId() throws SQLException, ClassNotFoundException {
        txtItemCode.setText(itemBO.CreateItemId());
    }

    public void clearTxtOnAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQonHand.clear();
        txtDiscount.clear();
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<JFXTextField> fields = new ArrayList<>();
        fields.add(txtDescription);
        fields.add(txtPackSize);
        fields.add(txtUnitPrice);
        fields.add(txtQonHand);
        fields.add(txtDiscount);

        ArrayList<String> regex = new ArrayList<>();
        regex.add("^[A-z]{3,50}$");
        regex.add("^[A-z0-9]{3,}$");
        regex.add("^[1-9][0-9]*([.][0-9]{2})?$");
        regex.add("^[0-9]{1,}$");
        regex.add("^[0-9][0-9]*([.][0-9]{2})?$");

        Object validate = Validate.validate(fields, regex);
        if (validate instanceof String){
            new Alert(Alert.AlertType.WARNING,"Invalid Value \""+validate+"\"").show();
            return;
        }else if(validate==null){
            new Alert(Alert.AlertType.WARNING,"Fill all the TextFields").show();
            return;
        }

        ItemDTO itemDTO = new ItemDTO(
                txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQonHand.getText()),Double.parseDouble(txtDiscount.getText())
        );


        if(itemBO.addItem(itemDTO)){
            new Alert(Alert.AlertType.INFORMATION,"Item added successfully").show();
            loadItemTable();
        }else{
            new Alert(Alert.AlertType.WARNING,"Error.Try Again latter").show();
        }
    }
}
