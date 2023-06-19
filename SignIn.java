package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.demo1.Data.dataHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignIn {

    @FXML
    private Button button_LogIn;
    @FXML
    private Button button_sigUp;
    @FXML
    private ComboBox<String> cb_typeOfUser;
    private final String[] type = {"Teacher" , "Student"};
    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_surname;
    @FXML
    private TextField tf_username;
    @FXML
    void initialize() {
        cb_typeOfUser.getItems().addAll(type);
        var ref = new Object() {
            String selectedItem;
        };
        cb_typeOfUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Student")) {
                ref.selectedItem = "Student";
            } else if (newValue.equals("Teacher")) {
                ref.selectedItem = "Teacher";
            }
        });
        button_sigUp.setOnAction(actionEvent -> {
            writeData(tf_username.getText(),tf_password.getText(), ref.selectedItem);
        });
        button_LogIn.setOnMouseClicked(actionEvent ->{
            button_LogIn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LogIn.fxml"));
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
    private void writeData(String loginText, String loginPassword, String selectedItem) {


        dataHandler dbHandler = new dataHandler();
        ResultSet result = null;

        if(selectedItem.contains("Student")){
            Student users = new Student();
            users.setUsername(loginText);
            users.setPassword(loginPassword);

            result = dbHandler.getStudent(users);
        }
        else{
            Teacher users = new Teacher();
            users.setUsername(loginText);
            users.setPassword(loginPassword);

            result = dbHandler.getTeacher(users);
        }

        int c = 0;

        while (true){
            try {
                if (!result.next())
                    break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            c++;
        }
        if(c >=1){
            Shake loginH = new Shake(tf_username);
            Shake pas = new Shake(tf_password);
            Shake name1 = new Shake(tf_name);
            Shake  surnam = new Shake(tf_surname);

            loginH.playAnim();
            name1.playAnim();
            surnam.playAnim();
            pas.playAnim();
        }
        else{
            dataHandler databaseHandler = new dataHandler();

            String name = this.tf_name.getText();
            String surname = this.tf_surname.getText();
            String username = tf_username.getText();
            String password = tf_password.getText();

            if(selectedItem == "Teacher"){
                Teacher user = new Teacher(name,surname,username,password);
                databaseHandler.signUpTeacher(user);
            }
            else{
                Student user = new Student(name,surname,username,password);
                databaseHandler.signUpStudent(user);
            }
        }

    }
}
