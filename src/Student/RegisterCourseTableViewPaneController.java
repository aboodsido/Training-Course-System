/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Student;

import Classes.course_room;
import static Student.StudentMainPageController.username;
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
import javafx.scene.control.TextField;
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
public class RegisterCourseTableViewPaneController implements Initializable {

    static String username;
    @FXML
    private Button backToMainbtn;

    public void openPage(String admin_user) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Registering Course Page");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("RegisterCourseTableViewPane.fxml"))));

        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();

        System.out.println(admin_user + " is loged in");
        username = admin_user;
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username + " now is on Register Course Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private HBox label_rejisterCourse;
    @FXML
    private Label label_welcome_trainer;
    @FXML
    private TextField search_by_course_name_textField;
    @FXML
    private Button search_btn;
    @FXML
    private Button show_all_btn;
    @FXML
    private HBox tabelView_registerCourse;
    @FXML
    private TableView<course_room> course_room_tableView;
    @FXML
    private TableColumn<course_room, String> course_id_column;
    @FXML
    private TableColumn<course_room, String> course_name_column;
    @FXML
    private TableColumn<course_room, String> course_time_column;
    @FXML
    private TableColumn<course_room, String> course_room_column;
    @FXML
    private Button register_btn;
    /**
     * Initializes the controller class.
     */
    Connection connection;
    ResultSet rs;

    ObservableList<course_room> courses_room = FXCollections.observableArrayList();

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
//        student_tableView.getColumns().addAll(std_id_column, std_name_column, std_age_column, std_phone_column, std_address_column);

        course_room_tableView.getSelectionModel().selectedIndexProperty().addListener(e -> {

        });

    }

    @FXML
    private void btnSearchHandle(ActionEvent event) throws SQLException {

        courses_room.clear();
        String searchField = search_by_course_name_textField.getText();
        Statement s = connection.createStatement();
        String query = "SELECT cm.course_id, c.course_name, cm.room_id, cm.time\n"
                + "FROM course c JOIN course_room cm ON cm.course_id = c.course_id\n"
                + "WHERE c.course_name = '" + searchField + "';";
        rs = s.executeQuery(query);
        while (rs.next()) {
            course_room cm = new course_room();
            cm.setCourse_id(rs.getString("course_id"));
            cm.setCourse_name(rs.getString("course_name"));
            cm.setRoom_id(rs.getString("room_id"));
            cm.setTime(rs.getString("time"));
            courses_room.add(cm);
        }
        course_room_tableView.setItems(courses_room);
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username + " clicked on search course button");
            logger.getLogger().info("Tying to search about " + searchField);
            logger.getLogger().info("QUERY: " + query);
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnShowAllHandle(ActionEvent event) throws SQLException {
        this.showSAllCourseRoom();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username + " Shows all not registered courses");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void register_course_action(ActionEvent event) throws SQLException {

        try {
            course_room cm = course_room_tableView.getSelectionModel().getSelectedItem();
            String course_id = cm.getCourse_id();
            String room_id = cm.getRoom_id();
            String time = cm.getTime();
            Statement s = connection.createStatement();
            String sql = "INSERT INTO student_course VALUES ('" + username + "', '" + course_id + "', '" + room_id + "', '" + time + "');";
            s.executeUpdate(sql);
            this.showSAllCourseRoom();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " registered a course its id: " + course_id);
                logger.getLogger().info("QUERY: " + sql);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Course Register Error:");
            alert.setHeaderText("No Selected Course");
            alert.setContentText("SORRY,  u didnt select any course to register. Please select course from the table and then register it");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " couldn't registed a course because there is not selected course");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void btnBackToMainHandle(ActionEvent event) throws IOException {
        System.out.println("register user: " + username);
        StudentMainPageController s = new StudentMainPageController();
        s.openPage(username);
        System.out.println("student user in back to main in register: " + username);
        Stage currentStage = (Stage) backToMainbtn.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Back to Student Main Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSAllCourseRoom() throws SQLException {

        courses_room.clear();
        Statement s = connection.createStatement();

        rs = s.executeQuery("SELECT cm.course_id, c.course_name, cm.room_id, cm.time\n"
                + "FROM course_room cm JOIN course c ON cm.course_id = c.course_id\n"
                + "WHERE cm.course_id IN (\n"
                + "SELECT course_id\n"
                + "FROM course_room\n"
                + "WHERE course_id NOT IN (\n"
                + "	SELECT c.course_id\n"
                + "    FROM student_course sc JOIN course c ON sc.course_id = c.course_id\n"
                + "    WHERE student_user = '" + username + "'));");
        while (rs.next()) {
            course_room cm = new course_room();
            cm.setCourse_id(rs.getString("course_id"));
            cm.setCourse_name(rs.getString("course_name"));
            cm.setRoom_id(rs.getString("room_id"));
            cm.setTime(rs.getString("time"));
            courses_room.add(cm);
        }
        course_room_tableView.setItems(courses_room);
    }
}
