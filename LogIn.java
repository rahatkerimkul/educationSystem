package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.demo1.Common.Common;
import com.example.demo1.Data.dataHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogIn {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button button_login;
    @FXML
    private Hyperlink button_sigin;
    @FXML
    private PasswordField tx_password;
    @FXML
    private TextField tx_username;

    @FXML
    void initialize() {
        button_login.setOnAction(actionEvent -> {
            try {
                findUser(tx_username.getText(), tx_password.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        button_sigin.setOnMouseClicked(actionEvent ->{
            button_sigin.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SigIn.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        });
    }

    private void findUser(String loginText, String loginPassword) throws SQLException {
        dataHandler dbHandler = new dataHandler();
        ResultSet result = null;
        Student users = new Student();
        users.setUsername(loginText);
        users.setPassword(loginPassword);
        result = dbHandler.getStudent(users);

        ResultSet result1 = null;
        Teacher user = new Teacher();
        user.setUsername(loginText);
        user.setPassword(loginPassword);
        result1 = dbHandler.getTeacher(user);
        if(result.next()){
            Common c = new Common(loginText, loginPassword);
            button_login.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("StudentsPage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
        else if (result1.next()) {
            Common c = new Common(loginText, loginPassword);
            button_login.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("IntoTheCourse.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } else if (loginText.contains("admin") && loginPassword.contains("admin")) {
            button_login.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminsPage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } else{
            Shake loginH = new Shake(tx_username);
            Shake pas = new Shake(tx_password);
            loginH.playAnim();
            pas.playAnim();
        }

    }

}
