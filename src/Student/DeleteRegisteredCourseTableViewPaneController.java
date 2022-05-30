/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Student;

import Classes.course_room;

import static Student.RegisterCourseTableViewPaneController.username;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logger.SystemLogger;
import login.LoginPagePaneController;

/**
 * FXML Controller class
 *
 * @author Sword
 */
public class DeleteRegisteredCourseTableViewPaneController implements Initializable {

    static String username;
    @FXML
    private TableView<course_room> registered_courses_tableView;
    @FXML
    private TableColumn<course_room, String> course_id_column;
    @FXML
    private TableColumn<course_room, String> course_name_column;
    @FXML
    private TableColumn<course_room, String> course_room_column;
    @FXML
    private TableColumn<course_room, String> course_time_column;
    @FXML
    private Button backToMainbtn;
    @FXML
    private Button show_registered;

    public void openPage(String admin_user) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Deleting Registered Course Page");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("DeleteRegisteredCourseTableViewPane.fxml"))));

        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();

        System.out.println(admin_user + " is loged in");
        username = admin_user;
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username + " now is on Delete Registered Course Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private HBox label_rejisterCourse;
    @FXML
    private Label label_delete_course;
    @FXML
    private HBox tabelView_deleteCourse;
    @FXML
    private Button delete_btn;

    Connection connection;
    ResultSet rs;

    ObservableList<course_room> registered_courses = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aam_training_center_db", "root", "");
//            Statement s = connection.createStatement();
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("successful connection to database");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPagePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

        course_id_column.setCellValueFactory(new PropertyValueFactory("course_id"));
        course_name_column.setCellValueFactory(new PropertyValueFactory("course_name"));
        course_room_column.setCellValueFactory(new PropertyValueFactory("room_id"));
        course_time_column.setCellValueFactory(new PropertyValueFactory("time"));
    }

    @FXML
    private void btnBackToMainHandle(ActionEvent event) throws IOException {

//        System.out.println("register user: " + username);
        StudentMainPageController s = new StudentMainPageController();
        s.openPage(username);
        System.out.println("student user in back to main in delete: " + username);
        Stage currentStage = (Stage) backToMainbtn.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Back to Student Main Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void show_registered_courses_action(ActionEvent event) throws SQLException {
        this.showSAllRegisteredCourses();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username + " Shows all registered courses");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void delete_button_action(ActionEvent event) throws SQLException {

        try {
            course_room cm = registered_courses_tableView.getSelectionModel().getSelectedItem();
            String course_id = cm.getCourse_id();
            Statement s = connection.createStatement();
            String sql = "DELETE FROM student_course WHERE student_user = '" + username + "' AND course_id = '" + course_id + "';";
            s.executeUpdate(sql);
            this.showSAllRegisteredCourses();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " Deleted a course its id: " + course_id);
                logger.getLogger().info("QUERY: " + sql);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Course Delete Error:");
            alert.setHeaderText("No Selected Course");
            alert.setContentText("SORRY,  You didnt select any course to delete. Please select course from the table and then delete it");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " couldn't delete a course because there is not selected course");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void showSAllRegisteredCourses() throws SQLException {
        System.out.println("student user in show all registered courses methodin delete: " + username);
        System.out.println("I am here");
        registered_courses.clear();
        Statement s = connection.createStatement();
        rs = s.executeQuery("SELECT sc.course_id, c.course_name, sc.room_id, sc.time\n"
                + "FROM student_course sc JOIN course c ON sc.course_id = c.course_id\n"
                + "WHERE student_user = '" + username + "';");
        System.out.println("SELECT sc.course_id, c.course_name, sc.room_id, sc.time\n"
                + "FROM student_course sc JOIN course c ON sc.course_id = c.course_id\n"
                + "WHERE student_user = '" + username + "';");
        System.out.println("---------hereherehere");
        while (rs.next()) {
            course_room cm = new course_room();
            cm.setCourse_id(rs.getString("course_id"));
            cm.setCourse_name(rs.getString("course_name"));
            cm.setRoom_id(rs.getString("room_id"));
            cm.setTime(rs.getString("time"));
            registered_courses.add(cm);
            System.out.println("--------" + cm.getCourse_id() + cm.getCourse_name());
        }
        registered_courses_tableView.setItems(registered_courses);
    }
}
