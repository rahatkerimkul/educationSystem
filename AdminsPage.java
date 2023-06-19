package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.demo1.Data.dataHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminsPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_addCourse;

    @FXML
    private Button button_clearCourses;

    @FXML
    private Button button_clearStudents;

    @FXML
    private Button button_clearTeachers;

    @FXML
    private Button button_courses;

    @FXML
    private Button button_logOut;

    @FXML
    private Button button_students;

    @FXML
    private Button button_teachers;

    @FXML
    private TextArea textArea_Courses;

    @FXML
    private TextArea textArea_Students;

    @FXML
    private TextArea textArea_teachers;

    @FXML
    private TextField tx_CourseName;

    @FXML
    private TextField tx_amountOfCredits;

    @FXML
    void initialize() {
        button_courses.setOnMouseClicked(actionEvent ->{
            String s= "";
            dataHandler dataHandler = new dataHandler();
            for(Courses c: dataHandler.getAllCourses()){
                s += c.getCourseName() +  " " + c.getAmountOfCredits() +"\n";
            }
            this.textArea_Courses.setText(s);
        });
        button_teachers.setOnMouseClicked(actionEvent ->{
            String s= "";
            dataHandler dataHandler = new dataHandler();
            for(Teacher c: dataHandler.getAllTeachers()){
                s += c.getName() + " " +  c.getSurname() + " " + c.getCourse() +"\n";
            }
            this.textArea_teachers.setText(s);
        });
        button_students.setOnMouseClicked(actionEvent ->{
            String s= "";
            dataHandler dataHandler = new dataHandler();
            for(Student c: dataHandler.getAllStudents()){
                s += c.getName() + " " +  c.getSurname() + "\n";
            }
            this.textArea_Students.setText(s);
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
        button_addCourse.setOnAction(actionEvent -> {
            writeData(tx_CourseName.getText(),tx_amountOfCredits.getText());
        });
        button_clearCourses.setOnAction(actionEvent -> {
            dataHandler dataHandler = new dataHandler();
            dataHandler.ClearCourses();
        });
        button_clearStudents.setOnAction(actionEvent -> {
            dataHandler dataHandler = new dataHandler();
            dataHandler.ClearStudent();
        });
        button_clearTeachers.setOnAction(actionEvent -> {
            dataHandler dataHandler = new dataHandler();
            dataHandler.ClearTeacher();
        });
}
    private void writeData(String CourseName, String amountOfCredits) {

        dataHandler dbHandler = new dataHandler();
        ResultSet result = null;
        Courses courses = new Courses();
        courses.setCourseName(CourseName);
        courses.setAmountOfCredits(amountOfCredits);

        result = dbHandler.getCourse(courses);


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
            Shake loginH = new Shake(tx_CourseName);
            Shake pas = new Shake(tx_amountOfCredits);


            loginH.playAnim();
            pas.playAnim();
        }
        else{
            dataHandler databaseHandler = new dataHandler();

            String name = this.tx_CourseName.getText();
            String credits = this.tx_amountOfCredits.getText();

            Courses courses1 = new Courses(name,credits);
            databaseHandler.giveCourse(courses1);

        }
    }
}

