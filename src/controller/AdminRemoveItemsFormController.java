package controller;

import com.jfoenix.controls.JFXTextField;
import bo.BOFactory;
import bo.custom.ItemBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;
import view.tm.ItemTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AdminRemoveItemsFormController {

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
            loadItemTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
    private void loadItemTable() throws SQLException, ClassNotFoundException {
        observableList.clear();

        ArrayList<ItemDTO> allItemDetails = itemBO.getAllItemDetails();
        for (ItemDTO dto : allItemDetails) {
            observableList.add(new ItemTM(dto.getItemCOde(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
        }
        tblItems.setItems(observableList);
    }

    public void clearAllOnAction() {
        txtDescription.clear();
        txtItemCode.clear();
        txtPackSize.clear();;
        txtUnitPrice.clear();
        txtQonHand.clear();
        txtDiscount.clear();
    }

    public void deleteAllOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                , "Do you want to Delete this item?", yes, no);
        alert.setTitle("Delete Confirmation");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            if(itemBO.deleteItem(txtItemCode.getText())){
                clearAllOnAction();
                loadItemTable();

            }else{
                new Alert(Alert.AlertType.WARNING,"Error,Try Again").show();
            }
        } else {

        }

    }

    public void searchItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = itemBO.searchItem(txtItemCode.getText());
        if(itemDTO ==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set").show();
        }else{
            txtDescription.setText(itemDTO.getDescription());
            txtPackSize.setText(itemDTO.getPackSize());
            txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
            txtQonHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
            txtDiscount.setText(String.valueOf(itemDTO.getDiscount()));
        }
    }
}
