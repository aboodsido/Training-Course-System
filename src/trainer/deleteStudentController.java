package trainer;

import Classes.course_room;
import Classes.student_course;
import Student.StudentMainPageController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logger.SystemLogger;
import login.LoginPagePaneController;
import static trainer.AddNewCourseToTrainerController.username;

public class deleteStudentController implements Initializable {

    static String username;
    @FXML
    private Button backToMainbtn;

    public void openPage(String admin_user) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Deleting Student Page");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("deleteStudent.fxml"))));

        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();

        System.out.println(admin_user + " is loged in");
        username = admin_user;
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+ " now is on Delete Course Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private Button delete_button;

    @FXML
    private Label deletingStudent_lable;

    @FXML
    private Button search_button;

    @FXML
    private TextField search_textField;

    @FXML
    private Button showStudents_button;

    @FXML
    private TableView<student_course> tableView;

    @FXML
    private TableColumn<student_course, String> student_user_column;
    @FXML
    private TableColumn<student_course, String> course_id_column;
    @FXML
    private TableColumn<student_course, String> room_id_column;
    @FXML
    private TableColumn<student_course, String> time_column;

    Connection connection;
    ResultSet rs;

    ObservableList<student_course> student_course = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aam_training_center_db", "root", "");
//            Statement s = connection.createStatement();
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("successful connection to database");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPagePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

        student_user_column.setCellValueFactory(new PropertyValueFactory("student_user"));
        course_id_column.setCellValueFactory(new PropertyValueFactory("course_id"));
        room_id_column.setCellValueFactory(new PropertyValueFactory("room_id"));
        time_column.setCellValueFactory(new PropertyValueFactory("time"));

    }

    @FXML
    void delete_button_action(ActionEvent event) throws SQLException {

        try {
            student_course sc = tableView.getSelectionModel().getSelectedItem();
            String course_id = sc.getCourse_id();
            String student_id = sc.getStudent_user();
            Statement s = connection.createStatement();
            String sql = "DELETE FROM student_course WHERE student_user = '" + student_id + "' AND course_id = '" + course_id + "';";
            System.out.println(sql);
            s.executeUpdate(sql);
            this.showAllStudent();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " Deleted a course its id: " + course_id);
                logger.getLogger().info("QUERY: " + sql);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Student Error:");
            alert.setHeaderText("No Selected Student");
            alert.setContentText("SORRY,  You didnt select any Student to Delete. Please select Student from the table and then Delete it");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " couldn't delete a course because there is not selected course");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void search_button_action(ActionEvent event) throws SQLException {
        System.out.println("here --------------");
        student_course.clear();
        String searchField = search_textField.getText();
        Statement s = connection.createStatement();
        String query = "SELECT sc.student_user, sc.course_id, sc.room_id, sc.time\n"
                + "FROM student_course sc JOIN trainer_course tc ON sc.course_id = tc.course_id\n"
                + "WHERE tc.trainer_user = '" + username + "' AND sc.student_user = '" + searchField + "';";
        rs = s.executeQuery(query);
        while (rs.next()) {
            student_course sc = new student_course();
            sc.setStudent_user(rs.getString("student_user"));
            sc.setCourse_id(rs.getString("course_id"));
            sc.setRoom_id(rs.getString("room_id"));
            sc.setTime(rs.getString("time"));
            student_course.add(sc);
        }
        tableView.setItems(student_course);
        try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " searched for a student training on his courses");
                logger.getLogger().info(query);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    @FXML
    private void showStudents_action(ActionEvent event) throws SQLException {
        this.showAllStudent();
    }

    private void showAllStudent() throws SQLException {

        System.out.println("here --------------");
        student_course.clear();
        String searchField = search_textField.getText();
        Statement s = connection.createStatement();
        System.out.println("SELECT sc.student_user, sc.course_id, sc.room_id, sc.time\n"
                + "FROM student_course sc JOIN trainer_course tc ON sc.course_id = tc.course_id\n"
                + "WHERE tc.trainer_user = '" + username + "' AND sc.student_user = '" + searchField + "';");
        rs = s.executeQuery("SELECT sc.student_user, sc.course_id, sc.room_id, sc.time\n"
                + "FROM student_course sc JOIN trainer_course tc ON sc.course_id = tc.course_id\n"
                + "WHERE tc.trainer_user = '" + username + "';");
        while (rs.next()) {
            student_course sc = new student_course();
            sc.setStudent_user(rs.getString("student_user"));
            sc.setCourse_id(rs.getString("course_id"));
            sc.setRoom_id(rs.getString("room_id"));
            sc.setTime(rs.getString("time"));
            student_course.add(sc);
        }
        tableView.setItems(student_course);
    }

    @FXML
    private void btnBackToMainHandle(ActionEvent event) throws IOException {
        System.out.println("register user: " + username);
        TrainerPaneController t = new TrainerPaneController();
        t.openPage(username);
        System.out.println("trainer user in back to main in delete: " + username);
        Stage currentStage = (Stage) backToMainbtn.getScene().getWindow();
        currentStage.close();
        System.out.println("Showing Registering Course Page to the trainer");
    }
}
