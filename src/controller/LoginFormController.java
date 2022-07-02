package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {

    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;



    public void btnLogin_OnAction(ActionEvent actionEvent) {
        if(!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Invalid username and password, please try again").show();
            return;
        }


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep8_hello","root", "mysql");
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            stm.setString(1,txtUserName.getText());
            stm.setString(2,txtPassword.getText());
            ResultSet rst = stm.executeQuery();

            if(rst.next()){
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ManageCustomerForm.fxml"));
                Scene mainScene = new Scene(load);
                Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
                primaryStage.setScene(mainScene);

            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid username and password, please try again").show();
                txtUserName.requestFocus();
            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }


    private boolean isValidated() {
        return (txtUserName.getText().trim().length() >= 2 &&
                txtPassword.getText().trim().length() >= 2);
    }
}
