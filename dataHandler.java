package com.example.demo1.Data;

import com.example.demo1.Courses;
import com.example.demo1.Student;
import com.example.demo1.Teacher;
import com.example.demo1.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo1.Data.ConstUser.*;

public class dataHandler extends Config {
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql:// " + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }
    public void signUpStudent(Student user) throws RuntimeException {
        String insert = "INSERT INTO " + USER_TABLE + " (" +
                USERS_NAME + "," + ConstUser.USERS_SURNAME + "," +
                ConstUser.USER_USERNAME + "," + ConstUser.USERS_PASSWORD +
                ")" + " VALUES (?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getSurname());
            prSt.setString(3, user.getUsername());
            prSt.setString(4, user.getPassword());


            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void signUpTeacher(Teacher user) throws RuntimeException {
        String insert = "INSERT INTO " + ConstUser.USER_TABLE2 + " (" +
                USERS_NAME + "," + ConstUser.USERS_SURNAME + "," +
                ConstUser.USER_USERNAME + "," + ConstUser.USERS_PASSWORD +
                ")" + " VALUES (?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getSurname());
            prSt.setString(3, user.getUsername());
            prSt.setString(4, user.getPassword());


            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getStudent(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + USER_TABLE + " WHERE " +
                ConstUser.USER_USERNAME + "=? AND " + ConstUser.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUsername());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
    public ResultSet getTeacher(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + ConstUser.USER_TABLE2 + " WHERE " +
                ConstUser.USER_USERNAME + "=? AND " + ConstUser.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUsername());
            prSt.setString(2, user.getPassword());


            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String select = "SELECT * FROM " + USER_TABLE;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(USERS_NAME);
                String surname = resultSet.getString(USERS_SURNAME);
                String username = resultSet.getString(USER_USERNAME);


                Student student = new Student(name, surname);
                student.setUsername(username);
                students.add(student);
            }

            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return students;
    }
    public List<Teacher> getAllTeachers() {
        List<Teacher> students = new ArrayList<>();
        String select = "SELECT * FROM " + USER_TABLE2;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(USERS_NAME);
                String surname = resultSet.getString(USERS_SURNAME);
                String username = resultSet.getString(USER_USERNAME);
                String course = resultSet.getString(TEACHER_COURSE);


                Teacher student = new Teacher(name, surname);
                student.setUsername(username);
                student.setCourse(course);
                students.add(student);
            }

            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return students;
    }
    public List<Courses> getAllCourses() {
        List<Courses> courses = new ArrayList<>();
        String select = "SELECT * FROM " + ConstCourses.COURSE_TABLE;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(ConstCourses.COURSES_NAME);
                String credits = resultSet.getString(ConstCourses.COURSES_AMOUNTOFCREDITS);

                Courses course = new Courses(name, credits);
                courses.add(course);
            }

            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }
    public void giveCourse(Courses course) throws RuntimeException {
        String insert = "INSERT INTO " + ConstCourses.COURSE_TABLE + " (" +
                ConstCourses.COURSES_NAME + "," + ConstCourses.COURSES_AMOUNTOFCREDITS +
        ")" + " VALUES (?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, course.getCourseName());
            prSt.setString(2, course.getAmountOfCredits());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getCourse(Courses course) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + ConstCourses.COURSE_TABLE + " WHERE " +
                ConstCourses.COURSES_NAME + "=? AND " +  ConstCourses.COURSES_AMOUNTOFCREDITS + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, course.getCourseName());
            prSt.setString(2, course.getAmountOfCredits());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
    public void ClearCourses(){
        String url = "jdbc:mysql://localhost:3306/rahat";
        String username = "root";
        String password = "rahat2004";
        String tableName = "courses";

        String sql = "DELETE FROM " + tableName;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int rowsDeleted = statement.executeUpdate();
            System.out.println("Deleted " + rowsDeleted + " rows from courses table.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ClearStudent(){
        String url = "jdbc:mysql://localhost:3306/rahat";
        String username = "root";
        String password = "rahat2004";
        String tableName = "student";

        String sql = "DELETE FROM " + tableName;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int rowsDeleted = statement.executeUpdate();
            System.out.println("Deleted " + rowsDeleted + " rows from"  + tableName + "table.");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ClearTeacher(){
        String url = "jdbc:mysql://localhost:3306/rahat";
        String username = "root";
        String password = "rahat2004";
        String tableName = "teacher";

        String sql = "DELETE FROM " + tableName;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int rowsDeleted = statement.executeUpdate();
            System.out.println("Deleted " + rowsDeleted + " rows from"  + tableName + "table.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void TeacherCourse(Teacher teacher, String course) throws RuntimeException {
        String insert = "UPDATE Teacher SET course = ? WHERE username = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, course);
            prSt.setString(2, teacher.getUsername());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void EvaluateStudent(String mrk, String courseName, Student student) throws SQLException, ClassNotFoundException {


        DatabaseMetaData metaData = getDbConnection().getMetaData();
        ResultSet columns = metaData.getColumns(null, null, "rahat.student", courseName);
        if(columns.next()){
            String insert = "ALTER TABLE rahat.student ADD COLUMN " + courseName + " VARCHAR(45) NULL;";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String insert2 = "UPDATE student SET " + courseName +" = ? WHERE username = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert2);
            prSt.setString(1, mrk);
            prSt.setString(2, student.getUsername());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}

