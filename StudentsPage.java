package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.demo1.Common.Common;
import com.example.demo1.Data.dataHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StudentsPage {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button button_courses;
    @FXML
    private Button button_T;
    @FXML
    private Button button_logOut;
    @FXML
    private TextArea courses_names;
    @FXML
    private TextArea teachers;

    @FXML
    void initialize() {
        button_courses.setOnMouseClicked(actionEvent ->{
            String s = "";
            dataHandler dataHandler = new dataHandler();
            for(Courses c: dataHandler.getAllCourses()){

                s += c.getCourseName() + " " + c.getAmountOfCredits() + "\n";
            }
            this.courses_names.setText(s);
                });
        button_T.setOnMouseClicked(actionEvent ->{
            String s= "";
            dataHandler dataHandler = new dataHandler();
            for(Teacher c: dataHandler.getAllTeachers()){
                s += c.getName() + " " +  c.getSurname() + "\n";
            }
            this.teachers.setText(s);
        });
        button_logOut.setOnMouseClicked(actionEvent ->{
            button_logOut.getScene().getWindow().hide();

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
}