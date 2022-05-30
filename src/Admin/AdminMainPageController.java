/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Admin;

import Student.StudentMainPageController;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logger.SystemLogger;

/**
 * FXML Controller class
 *
 * @author Sword
 */
public class AdminMainPageController implements Initializable {

    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void openPage(String admin_user) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Admin main page");
        stage.setResizable(false);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("AdminMainPage.fxml"))));
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        this.setUsername(admin_user);
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username + " is logged in");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private Button student_operations_button;
    @FXML
    private Button trainer_operations_button;
    @FXML
    private Button courses_operations_button;
    @FXML
    private Button room_operations_button;
    @FXML
    private Button reports_button;
    @FXML
    private Button log_out;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    Parent pane;
    Stage stage;
    Stage currentStage;

    @FXML
    private void student_operations_action(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("StudentOperationsTableViewPane.fxml"));
        stage = new Stage();
        stage.setTitle("Student Operations Page");
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        currentStage = (Stage) student_operations_button.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Clicked on Student Operation button");
            logger.getLogger().info("Showing Student Operations Page to the Admin");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void trainer_operations_action(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("trainerOperationsTableViewPane.fxml"));
        stage = new Stage();
        stage.setTitle("Trainer Operations Page");
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        currentStage = (Stage) student_operations_button.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Clicked on Trainer Operation button");
            logger.getLogger().info("Showing Trainer Operations Page to the Admin");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Showing teainer operation page to the ADMIN");
    }

    @FXML
    private void courses_operations_action(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("CourseOperationsTableViewPane.fxml"));
        stage = new Stage();
        stage.setTitle("Courses Operations Page");
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        currentStage = (Stage) student_operations_button.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Clicked on Course Operation button");
            logger.getLogger().info("Showing Course Operations Page to the Admin");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Showing courses operation page to the ADMIN");
    }

    @FXML
    private void room_operations_action(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("roomOperationsTableViewPane.fxml"));
        stage = new Stage();
        stage.setTitle("Room Operations Page");
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        currentStage = (Stage) student_operations_button.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Clicked on Room Operation button");
            logger.getLogger().info("Showing Room Operations Page to the Admin");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Showing room operation page to the ADMIN");
    }

    @FXML
    private void reports_action(ActionEvent event) {
    }

    @FXML
    private void log_out_action(ActionEvent event) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("../login/loginPagePane.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login Page");
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        Stage currentStage = (Stage) log_out.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin just logged out");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        System.out.println("Showing login page to the STUDENT after logging out");
    }

}
