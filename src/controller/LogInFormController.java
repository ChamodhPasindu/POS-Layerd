package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInFormController {
    public AnchorPane loginContext;
    public JFXTextField txtName;
    public JFXPasswordField txtPassword;


    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {

        if(txtName.getText().equals("admin") && txtPassword.getText().equals("1234")) {

            Parent load = FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"));
            Scene scene = new Scene(load);
            Stage window = (Stage) loginContext.getScene().getWindow();
            window.setScene(scene);
            return;
        }else if(txtName.getText().equals("user") && txtPassword.getText().equals("1234")){

            Parent load1 = FXMLLoader.load(getClass().getResource("../view/RecDashBoardForm.fxml"));
            Scene scene1 = new Scene(load1);
            Stage window1 = (Stage) loginContext.getScene().getWindow();
            window1.setScene(scene1);
            return;

        }else{
            new Alert(Alert.AlertType.WARNING,"The user name or password that you entered is incorrect.Try Again.. ", ButtonType.CLOSE).show();
        }
    }
}
