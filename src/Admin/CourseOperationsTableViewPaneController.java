/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Admin;

import Classes.Room;
import Classes.Student;
import Classes.course_room;
import Student.StudentMainPageController;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logger.SystemLogger;
import login.LoginPagePaneController;

/**
 * FXML Controller class
 *
 * @author Sword
 */
public class CourseOperationsTableViewPaneController implements Initializable {

    @FXML
    private Label course_id_label;
    @FXML
    private TextField course_id_field;
    @FXML
    private Label course_description_label;
    @FXML
    private TextArea course_description_area;
    @FXML
    private TableView<course_room> course_tableView;
    @FXML
    private TableColumn<course_room, String> course_id_column;
    @FXML
    private TableColumn<course_room, String> course_description_column;
    @FXML
    private TableColumn<course_room, String> room_id_column;
    @FXML
    private TableColumn<course_room, String> lec_time_column;
    @FXML
    private TableColumn<course_room, String> course_name_column;
    @FXML
    private TextField lecture_time_field;

    @FXML
    private Button btnShow;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnBackToMain;
    @FXML
    private Label room_id_label;
    @FXML
    private Label lecture_time_label;
    @FXML
    private ComboBox<String> room_id_comboBox;

    Connection connection;
    ResultSet rs;

    ObservableList<course_room> course_room = FXCollections.observableArrayList();
    @FXML
    private Label course_name_label;
    @FXML
    private TextField course_name_field;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aam_training_center_db", "root", "");
//            Statement s = connection.createStatement();
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("successful connection to database");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(LoginPagePaneController.class.getName()).log(Level.SEVERE, null, ex);
            }

            course_id_column.setCellValueFactory(new PropertyValueFactory("course_id"));
            course_description_column.setCellValueFactory(new PropertyValueFactory("description"));
            course_name_column.setCellValueFactory(new PropertyValueFactory("course_name"));
            lec_time_column.setCellValueFactory(new PropertyValueFactory("time"));
            room_id_column.setCellValueFactory(new PropertyValueFactory("room_id"));

            course_tableView.getSelectionModel().selectedIndexProperty().addListener(e -> {
                this.showSelectedCourse();
            });

            Statement s = connection.createStatement();
            rs = s.executeQuery("SELECT * FROM room");
            while (rs.next()) {
                room_id_comboBox.getItems().add(rs.getString("room_id"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CourseOperationsTableViewPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void show_action(ActionEvent event) throws SQLException {
        this.showAllCourses();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Shows all courses");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void add_action(ActionEvent event) {

        try {
            Statement s = connection.createStatement();

            String id = course_id_field.getText();
            String name = course_name_field.getText();
            String description = course_description_area.getText();
            String room_id = room_id_comboBox.getSelectionModel().getSelectedItem();
            String time = lecture_time_field.getText();

            String sql = "INSERT INTO course VALUES ('" + id + "', '" + name + "', '" + description + "');";
            s.executeUpdate(sql);
            String sql2 = "INSERT INTO course_room (`course_id`, `room_id`) VALUES ('" + id + "', '" + room_id + "');";
            s.executeUpdate(sql2);
            this.showAllCourses();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info("Admin Added Course");
                logger.getLogger().info("QUERY: " + sql);
                logger.getLogger().info("QUERY: " + sql2);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException n) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Student Add Error:");
            alert.setHeaderText("Number format error");
            alert.setContentText("sorry, u filled student inforamtion text fields of number format with Alphapitical format. Please fill it correctly and then try again.");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info("couldn't add course because student inforamtion text fields of number format with Alphapitical format");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Course Add Error:");
            alert.setHeaderText("Empty text fields");
            alert.setContentText("sorry, u didnt filled course inforamtion text fields. Please fill it and try to add course again.");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info("couldn't add course because of Empty text fields");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void edit_action(ActionEvent event) {

        try {
            course_room cm = course_tableView.getSelectionModel().getSelectedItem();
            String old_course_id = cm.getCourse_id();
            System.out.println(old_course_id);

            Statement s = connection.createStatement();
            String id = course_id_field.getText();
            String name = course_name_field.getText();
            String description = course_description_area.getText();
            String room_id = room_id_comboBox.getSelectionModel().getSelectedItem();
            String time = lecture_time_field.getText();

            String sql = "UPDATE `course` SET `course_id` = '" + id + "', `course_name` = '" + name + "', `description` = '" + description + "' WHERE `course`.`course_id` = '" + old_course_id + "' ";
            s.executeUpdate(sql);
            String sql2 = "UPDATE `course_room` SET `room_id` = '" + room_id + "' WHERE `course_room`.`course_id` = '" + id + "'; ";
            System.out.println(sql);
            s.executeUpdate(sql2);
            this.showAllCourses();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(" Edit a course its id: " + old_course_id);
                logger.getLogger().info("QUERY: " + sql);
                logger.getLogger().info("QUERY: " + sql2);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Course Edit Error:");
            alert.setHeaderText("No Selected Course");
            alert.setContentText("SORRY,  u didnt select any course to edit. Please select student from the table and then edit course");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(" couldn't edit a course because there is no selected course");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void delete_action(ActionEvent event) throws SQLException {
        Statement s = connection.createStatement();
        String id = course_id_field.getText();
        String sql = "DELETE FROM course WHERE `course`.`course_id` = '" + id + "';";
        s.executeUpdate(sql);
        this.resetCourseRoomTextFields();
        this.showAllCourses();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(" Deleted a course its id: " + id);
            logger.getLogger().info("QUERY: " + sql);
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void reset_action(ActionEvent event) {
        this.resetCourseRoomTextFields();
    }

    @FXML
    private void btnBackToMainHandle(ActionEvent event) throws IOException {

        Pane pane = FXMLLoader.load(getClass().getResource("AdminMainPage.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Admin Main Page");
        stage.setScene(new Scene(pane));
        stage.show();
        Stage currentStage = (Stage) btnBackToMain.getScene().getWindow();
        currentStage.close();
        System.out.println("Closing Student Operations Page and back to Admin Main Page");
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Back to Admin Main Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSelectedCourse() {
        course_room cm = course_tableView.getSelectionModel().getSelectedItem();
        if (cm != null) {
            course_id_field.setText(cm.getCourse_id());
            course_name_field.setText(cm.getCourse_name());
            course_description_area.setText(cm.getDescription());
            lecture_time_field.setText(cm.getTime());
            room_id_comboBox.getSelectionModel().select(cm.getRoom_id());
        }
    }

    public void showAllCourses() throws SQLException {
        course_room.clear();
        Statement s = connection.createStatement();
        rs = s.executeQuery("SELECT cm.course_id, c.course_name, c.description, cm.room_id, cm.time\n"
                + "FROM course c JOIN course_room cm ON c.course_id = cm.course_id");
        while (rs.next()) {
            course_room cm = new course_room();
            cm.setCourse_id(rs.getString("course_id"));
            cm.setDescription(rs.getString("description"));
            cm.setCourse_name(rs.getString("course_name"));
            cm.setRoom_id(rs.getString("room_id"));
            cm.setTime(rs.getString("time"));
            course_room.add(cm);
        }
        course_tableView.setItems(course_room);
    }

    private void resetCourseRoomTextFields() {
        course_id_field.setText("");
        course_name_field.setText("");
        course_description_area.setText("");
        lecture_time_field.setText("");
        room_id_comboBox.getSelectionModel().select("");
    }

}
