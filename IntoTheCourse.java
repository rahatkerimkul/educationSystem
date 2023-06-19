package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.demo1.Common.Common;
import com.example.demo1.Data.dataHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ImageInput;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.UserDataHandler;

public class IntoTheCourse {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button button_evaluateStudent;
    @FXML
    private Button button_logOut;
    @FXML
    private Button button_takeCourse;
    @FXML
    private ComboBox<String> cb_marks;
    private final String[] marks = {"4" , "3", "2", "1"};
    @FXML
    private Label label_Welcometext;
    @FXML
    private Label label_haveCourseOnTeacher;
    @FXML
    private Label label_studentEvaluated;
    @FXML
    private TableColumn<Courses, String> list_Coursesname;
    @FXML
    private TableColumn<Courses, String> list_Credits;
    @FXML
    private TableColumn<Student, String> list_nameStudent;
    @FXML
    private TableColumn<Student, String> list_surnameStudent;
    @FXML
    private TableView<Courses> table_courses;
    @FXML
    private TableView<Student> table_students;
    @FXML
    void initialize() {
        cb_marks.getItems().addAll(marks);
        dataHandler dataHandler = new dataHandler();
        List<Teacher> teachers = dataHandler.getAllTeachers();
        var ref = new Object() {
            Teacher teacher = null;
        };
        for (Teacher t: teachers){
            if(t.getUsername().contains(Common.loginText)){
                ref.teacher = t;
            }
        }
        label_Welcometext.setText("Welcome " + ref.teacher.getName() + " " + ref.teacher.getSurname() + "!!!");
        ObservableList<Courses> courseList = FXCollections.observableArrayList();
        courseList.addAll(dataHandler.getAllCourses());
        list_Coursesname.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        list_Credits.setCellValueFactory(new PropertyValueFactory<>("amountOfCredits"));
        table_courses.setItems(courseList);
        var ref1 = new Object() {
            String selectedCourse = "";
        };
        table_courses.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                 ref1.selectedCourse = table_courses.getSelectionModel().getSelectedItem().getCourseName();
            }
        });
        button_takeCourse.setOnAction(actionEvent -> {
            int r = 0;
            for(Courses c: dataHandler.getAllCourses()){
                if(c.getCourseName().equals(ref.teacher.getCourse())){
                    r++;
                }
            }
            if(r == 0){
                dataHandler.TeacherCourse(ref.teacher, ref1.selectedCourse);
                label_haveCourseOnTeacher.setText("You successfully took the course!");
            }
            else label_haveCourseOnTeacher.setText("You have the course named: " + ref.teacher.getCourse());
        });
        ObservableList<Student> studentslist = FXCollections.observableArrayList();
        studentslist.addAll(dataHandler.getAllStudents());
        list_nameStudent.setCellValueFactory(new PropertyValueFactory<>("name"));
        list_surnameStudent.setCellValueFactory(new PropertyValueFactory<>("surname"));
        table_students.setItems(studentslist);
        var ref2 = new Object() {
            Student student = null;
            String selectedItem = "";
        };
        table_students.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ref2.student = table_students.getSelectionModel().getSelectedItem();
            }
        });
        cb_marks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("4")) {
                ref2.selectedItem = "4";
            } else if (newValue.equals("3")) {
                ref2.selectedItem = "3";
            }
            else if (newValue.equals("2")) {
                ref2.selectedItem = "2";
            }
            else if (newValue.equals("1")) {
                ref2.selectedItem = "1";
            }
        });
        button_evaluateStudent.setOnAction(actionEvent -> {
            if(!ref2.selectedItem.equals("") && ref2.student != null){
                try {
                    dataHandler.EvaluateStudent(ref2.selectedItem, ref.teacher.getCourse(), ref2.student);
                    label_studentEvaluated.setText("You evaluated a student!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

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
