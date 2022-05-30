/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Student;

import Classes.course_room;
import static Student.DeleteRegisteredCourseTableViewPaneController.username;
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
public class ScheduleController implements Initializable {

    static String username;

    public void openPage(String admin_user) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Student Schedule Page");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Schedule.fxml"))));

        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();

        System.out.println(admin_user + " is loged in");
        username = admin_user;
        System.out.println("Student schedule page user: " + username);
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+ " now is on Courses Schdeule Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private HBox label_schedule;
    @FXML
    private Label label_delete_course;
    @FXML
    private HBox tabelView_schedule;
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
    private Button show_all_btn;
    @FXML
    private Button backToMainbtn;

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
    private void show_all_action(ActionEvent event) throws SQLException {
        this.showSAllRegisteredCourses();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+ "Showing student Schedule");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnBackToMainHandle(ActionEvent event) throws IOException {

        StudentMainPageController s = new StudentMainPageController();
        s.openPage(username);
        System.out.println("student user in back to main in schedule: " + username);
        Stage currentStage = (Stage) backToMainbtn.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Back to Student Main Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
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
