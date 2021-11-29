package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
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
import view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecDashBoardFormController {

    private ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);
    public AnchorPane dashBoardContext;
    public AnchorPane subContext;
    public TableView tbtCurrentItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colQtyOnHand;

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            loadCurrentItemDetail();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void openLoginFormOnAction(ActionEvent actionEvent) throws IOException {
       loadUiForDashContext("LogInForm");
    }

    public void openDashBoardFormOnAction(ActionEvent actionEvent) throws IOException {
        loadUiForDashContext("RecDashBoardForm");
    }

    public void openAddOrderOnAction(ActionEvent actionEvent) throws IOException {
        loadUiForSubContext("RecMakeCustomerOrderForm");
    }

    public void openManageOrdersOnAction(ActionEvent actionEvent) throws IOException {
        loadUiForSubContext("RecManageOrderForm");
    }

    private void loadUiForSubContext(String file) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/" + file + ".fxml"));
        subContext.getChildren().clear();
        subContext.getChildren().add(load);
    }

    private void loadUiForDashContext(String file) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/" + file + ".fxml"));
        Scene scene = new Scene(load);
        Stage window = (Stage) dashBoardContext.getScene().getWindow();
        window.setScene(scene);
    }

    ObservableList<ItemTM> itemTMObservableList = FXCollections.observableArrayList();
    private void loadCurrentItemDetail() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItemDTODetails =itemBO.getAllItemDetails();

        for (ItemDTO itemDTO : allItemDTODetails) {
            itemTMObservableList.add(new ItemTM(itemDTO.getItemCOde(),
                    itemDTO.getDescription(),
                    itemDTO.getPackSize(),
                    itemDTO.getUnitPrice(),
                    itemDTO.getQtyOnHand(),
                    itemDTO.getDiscount()));

        }
        tbtCurrentItem.setItems(itemTMObservableList);

    }



}
