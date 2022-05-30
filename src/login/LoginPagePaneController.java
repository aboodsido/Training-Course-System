/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package login;

import Admin.*;
import Student.StudentMainPageController;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logger.SystemLogger;
import trainer.TrainerPaneController;

/**
 * FXML Controller class
 *
 * @author Sword
 */
public class LoginPagePaneController implements Initializable {

    @FXML
    private Label username_label;
    @FXML
    private Label password_label;
    @FXML
    private TextField username_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Button login_button;
    @FXML
    private Button reset_button;
    @FXML
    private Button exit_button;
    @FXML
    private Label message_error;

    /**
     * Initializes the controller class.
     */
    Connection connection;
    ResultSet rs;

    Parent pane;
    Stage stage;
    Stage currentStage;

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
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("System is Opened - Login Page appears");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void login_action(ActionEvent event) throws SQLException, IOException, NoSuchAlgorithmException {
        Statement s = connection.createStatement();
        String username = username_field.getText();
        String password = password_field.getText();
        String EncryptedPassword = MD5.MD5EncryptingPassword.encryptingPassword(password);
        rs = s.executeQuery("SELECT * FROM login_info WHERE username = '" + username + "' and password = '" + EncryptedPassword + "';");
        System.out.println("SELECT * FROM login_info WHERE username = '" + username + "' and password = '" + EncryptedPassword + "';");
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Some one tried to login the system with username: " + username);
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ResultSetMetaData rsMetaData = rs.getMetaData();
//        System.out.println("--------------------------------" + rs.getRow());

        if (rs.next()) {
            String user = rs.getString("username");
            String pass = rs.getString("password");
            String user_cat = rs.getString("userCategory");
            System.out.println(user + " - " + pass + " - " + user_cat);
            switch (user_cat) {

                case "admin":
                    System.out.println("Admin is loged in");
                    AdminMainPageController Admin = new AdminMainPageController();
                    Admin.openPage(username);
//                    Stage currentStage;
                    currentStage = (Stage) login_button.getScene().getWindow();
                    currentStage.close();
                    System.out.println("Admin is loged in");
                    try {
                        SystemLogger logger = SystemLogger.getInstance();
                        logger.getLogger().info(username + "is logged in as Admin");
                    } catch (IOException ex) {
                        Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "student":

                    System.out.println("Student is loged in");
                    StudentMainPageController student = new StudentMainPageController();
                    student.openPage(username);
                    currentStage = (Stage) login_button.getScene().getWindow();
                    currentStage.close();
                    System.out.println("student is loged in");
                    try {
                        SystemLogger logger = SystemLogger.getInstance();
                        logger.getLogger().info(username + "is logged in as Student");
                    } catch (IOException ex) {
                        Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "trainer":
                    System.out.println("Trainer is loged in");
                    TrainerPaneController trainer = new TrainerPaneController();
                    trainer.openPage(username);
                    currentStage = (Stage) login_button.getScene().getWindow();
                    currentStage.close();
                    System.out.println("trainer is loged in");
                    try {
                        SystemLogger logger = SystemLogger.getInstance();
                        logger.getLogger().info(username + "is logged in as Trainer");
                    } catch (IOException ex) {
                        Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        } else {
            message_error.setText("Sorry - Invalid accessing information");
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(username + " -> login denied ");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void reset_action(ActionEvent event) {
        username_field.setText("");
        password_field.setText("");
        message_error.setText("");
    }

    @FXML
    private void exit_action(ActionEvent event) {
        currentStage = (Stage) exit_button.getScene().getWindow();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("The System Just Closed");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentStage.close();
    }

}
