package controller;

import com.jfoenix.controls.JFXTextField;
import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.PlaceOrderBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.ItemDTO;
import dto.CustomDTO;
import view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDashBoardFormController {

    private ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);
    private PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PLACE_ORDER);

    public AnchorPane dashBoardContext;
    public AnchorPane subContext;
    public JFXTextField txtMostItemCode;
    public JFXTextField txtLeastItemCode;
    public JFXTextField txtMostDescription;
    public JFXTextField txtLeastDescription;
    public JFXTextField txtMostQty;
    public JFXTextField txtLeastQty;
    public JFXTextField txtMostUnitPrice;
    public JFXTextField txtLeastUnitPrice;
    public JFXTextField txtMostDiscount;
    public JFXTextField txtLeastDiscount;
    public TableView tblItemList;
    public TableColumn colItemCode;
    public TableColumn colDescription;

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            loadMovableItemDetail();
            loadItemList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
    private void loadItemList() throws SQLException, ClassNotFoundException {
        List<ItemDTO> allItemDTOForAdmin = itemBO.getAllItems();
        for (ItemDTO itemDTO : allItemDTOForAdmin) {
            observableList.add(new ItemTM(itemDTO.getItemCOde(), itemDTO.getDescription()));
        }
        tblItemList.setItems(observableList);
    }

    private void loadMovableItemDetail() throws SQLException, ClassNotFoundException {
        ArrayList<CustomDTO> customDTO =placeOrderBO.findMovableItem();
        if(customDTO.isEmpty()){

        }else{
           txtMostItemCode.setText(customDTO.get(0).getItemCode());
           txtLeastItemCode.setText(customDTO.get(customDTO.size()-1).getItemCode());

            ItemDTO itemDTOMost = itemBO.searchItem(txtMostItemCode.getText());
            txtMostDescription.setText(itemDTOMost.getDescription());
            txtMostQty.setText(String.valueOf(itemDTOMost.getQtyOnHand()));
            txtMostUnitPrice.setText(String.valueOf(itemDTOMost.getUnitPrice()));
            txtMostDiscount.setText(String.valueOf(itemDTOMost.getDiscount()));

            ItemDTO itemDTOLeast = itemBO.searchItem(txtLeastItemCode.getText());
            txtLeastDescription.setText(itemDTOLeast.getDescription());
            txtLeastQty.setText(String.valueOf(itemDTOLeast.getQtyOnHand()));
            txtLeastUnitPrice.setText(String.valueOf(itemDTOLeast.getUnitPrice()));
            txtLeastDiscount.setText(String.valueOf(itemDTOLeast.getDiscount()));


        }
    }

    public void loadUi(String file) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/" + file + ".fxml"));
        subContext.getChildren().clear();;
        subContext.getChildren().add(load);
    }

    public void openDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"));
        Scene scene = new Scene(load);
        Stage window = (Stage) dashBoardContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void openAdminAddItemOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AdminAddItemForm");
    }

    public void openAdminManageItemsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AdminManageItemsForm");

    }

    public void openAdminRemoveItemsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AdminRemoveItemsForm");
    }

    public void openAdminDashBoardFormOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"));
        Scene scene = new Scene(load);
        Stage window = (Stage) dashBoardContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void openIncomeReportsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AdminIncomeReports");
    }
}
