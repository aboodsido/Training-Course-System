/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Admin;

import Classes.Student;
import Student.StudentMainPageController;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logger.SystemLogger;
import login.LoginPagePaneController;

/**
 * FXML Controller class
 *
 * @author Sword
 */
public class StudentOperationsTableViewPaneController implements Initializable {

    @FXML
    private Label id_label;
    @FXML
    private Label name_label;
    @FXML
    private Label age_label;
    @FXML
    private Label phone_label;
    @FXML
    private Label address_label;
    @FXML
    private TextField std_id_field;
    @FXML
    private TextField std_name_field;
    @FXML
    private TextField std_age_field;
    @FXML
    private TextField std_phone_field;
    @FXML
    private TableView<Student> student_tableView;
    @FXML
    private TableColumn<Student, String> std_id_column;
    @FXML
    private TableColumn<Student, String> std_name_column;
    @FXML
    private TableColumn<Student, Integer> std_age_column;
    @FXML
    private TableColumn<Student, Integer> std_phone_column;
    @FXML
    private TableColumn<Student, String> std_address_column;
    @FXML
    private Button show_button;
    @FXML
    private Button add_button;
    @FXML
    private Button edit_button;
    @FXML
    private Button delete_button;
    @FXML
    private Button reset_button;
    @FXML
    private Button btnBackToMain;
    @FXML
    private TextField std_address_field;
    @FXML
    private Label password_label;
    @FXML
    private TextField password_field;

    Parent pane;
    Stage stage;
    Stage currentStage;

    /**
     * Initializes the controller class.
     */
    Connection connection;
    ResultSet rs;

    ObservableList<Student> students = FXCollections.observableArrayList();

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

        std_id_column.setCellValueFactory(new PropertyValueFactory("student_user"));
        std_name_column.setCellValueFactory(new PropertyValueFactory("name"));
        std_age_column.setCellValueFactory(new PropertyValueFactory("age"));
        std_phone_column.setCellValueFactory(new PropertyValueFactory("phone_number"));
        std_address_column.setCellValueFactory(new PropertyValueFactory("address"));
//        student_tableView.getColumns().addAll(std_id_column, std_name_column, std_age_column, std_phone_column, std_address_column);

        student_tableView.getSelectionModel().selectedIndexProperty().addListener(e -> {
            this.resetStudentTextFields();
            this.showSelectedStudent();
        });

    }

    @FXML
    private void show_action(ActionEvent event) throws SQLException {
        this.showAllStudent();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Shows all Students");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void add_action(ActionEvent event) throws SQLException, NoSuchAlgorithmException {

        try {
            Statement s = connection.createStatement();

            String id = std_id_field.getText();
            String name = std_name_field.getText();
            Integer age = Integer.parseInt(std_age_field.getText());
            Integer phone_number = Integer.parseInt(std_phone_field.getText());
            String address = std_address_field.getText();
            String password = MD5.MD5EncryptingPassword.encryptingPassword(password_field.getText());

            String sql = "INSERT INTO student VALUES ('" + id + "', '" + name + "','" + age + "'," + phone_number + ", '" + address + "');";
            s.executeUpdate(sql);
            String sql2 = "INSERT INTO login_info VALUES ('" + id + "', '" + password + "', 'student');";
            s.executeUpdate(sql2);
            this.showAllStudent();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info("Admin Added Student");
                logger.getLogger().info("QUERY: " + sql);
                logger.getLogger().info("QUERY: " + sql2);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException n) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Student Add Error:");
            alert.setHeaderText("Number format error");
            alert.setContentText("sorry, u filled student inforamtion text fields of number format with Alphapitical format. Please fill it correctly and then try again.");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info("couldn't add student because student inforamtion text fields of number format with Alphapitical format");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Student Add Error:");
            alert.setHeaderText("Empty text fields");
            alert.setContentText("sorry, u didnt filled student inforamtion text fields. Please fill it and try to add student again.");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info("couldn't add student because of Empty text fields");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void edit_action(ActionEvent event) throws SQLException {
        try {
            Student student = student_tableView.getSelectionModel().getSelectedItem();
            String old_username = student.getStudent_user();
            System.out.println(old_username);

            Statement s = connection.createStatement();
            String id = std_id_field.getText();
            String name = std_name_field.getText();
            Integer age = Integer.parseInt(std_age_field.getText());
            Integer phone_number = Integer.parseInt(std_phone_field.getText());
            String address = std_address_field.getText();

            String sql = "UPDATE student SET student_user='" + id + "', name ='" + name + "',age= '" + age + "',phone_number= " + phone_number + ", address = '" + address + "' WHERE student_user = '" + old_username + "';";
            String sql2 = "UPDATE login_info SET username = '" + id + "' WHERE username = '"+old_username+"';";
            System.out.println(sql);
            System.out.println(sql2);
            s.executeUpdate(sql);
            s.executeUpdate(sql2);
            this.showAllStudent();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(" Edit a student its id: " + old_username);
                logger.getLogger().info("QUERY: " + sql);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Student Edit Error:");
            alert.setHeaderText("No Selected Student");
            alert.setContentText("SORRY,  u didnt select any student to edit. Please select student from the table and then edit student");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(" couldn't edit a student because there is no selected student");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void reset_action(ActionEvent event) {
        this.resetStudentTextFields();
    }

    @FXML
    private void delete_action(ActionEvent event) throws SQLException {
        Statement s = connection.createStatement();
        String id = std_id_field.getText();
        String sql = "DELETE FROM student WHERE student_user = '" + id + "';";
        String sql2 = "DELETE FROM login_info WHERE username = '"+id+"';";
        s.executeUpdate(sql);
        s.executeUpdate(sql2);
        this.resetStudentTextFields();
        this.showAllStudent();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(" Deleted a student its id: " + id);
            logger.getLogger().info("QUERY: " + sql);
            logger.getLogger().info("QUERY: " + sql2);
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnBackToMainHandle(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("AdminMainPage.fxml"));
        stage = new Stage();
        stage.setTitle("Admin Main Page");
        stage.setScene(new Scene(pane));
        stage.show();
        currentStage = (Stage) btnBackToMain.getScene().getWindow();
        currentStage.close();
        System.out.println("Closing Student Operations Page and back to Admin Main Page");
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Back to Admin Main Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSelectedStudent() {
        Student student = student_tableView.getSelectionModel().getSelectedItem();
        if (student != null) {
            std_id_field.setText(student.getStudent_user());
            std_name_field.setText(student.getName());
            std_age_field.setText(String.valueOf(student.getAge()));
            std_phone_field.setText(String.valueOf(student.getPhone_number()));
            std_address_field.setText(student.getAddress());
        }
    }

    public void showAllStudent() throws SQLException {
        students.clear();
        Statement s = connection.createStatement();
        rs = s.executeQuery("Select * From student");
        while (rs.next()) {
            Student student = new Student();
            student.setStudent_user(rs.getString("student_user"));
            student.setName(rs.getString("name"));
            student.setAge(rs.getInt("age"));
            student.setPhone_number(rs.getInt("phone_number"));
            student.setAddress(rs.getString("address"));
            students.add(student);
        }
        student_tableView.setItems(students);
    }

    private void resetStudentTextFields() {
        std_id_field.setText("");
        std_name_field.setText("");
        std_age_field.setText("");
        std_phone_field.setText("");
        std_address_field.setText("");
        password_field.setText("");
    }

}
