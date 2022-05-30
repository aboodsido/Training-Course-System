/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Student;

import static Student.DeleteRegisteredCourseTableViewPaneController.username;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logger.SystemLogger;

/**
 * FXML Controller class
 *
 * @author Sword
 */
public class StudentMainPageController implements Initializable {
    
    static String username;
    
    
    public void openPage (String admin_user) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Student Main Page");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("StudentMainPage.fxml"))));

        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        
        System.out.println(admin_user+" is loged in");
        username = admin_user;
        System.out.println("student user when loggin to the main page: "+username);
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" is logged in");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private Label label_welcome_trainer;
    @FXML
    private Button my_schedule_btn;
    @FXML
    private Button reports_btn;
    @FXML
    private Button log_out_btn;
    @FXML
    private Button register_course_button;
    @FXML
    private Button deleter_registered_course_button;

    Parent pane;
    Stage stage;
    Stage currentStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void register_course_action(ActionEvent event) throws IOException {
        RegisterCourseTableViewPaneController rc = new RegisterCourseTableViewPaneController();
        rc.openPage(username);
        System.out.println("student user in register in main: "+username);
        currentStage = (Stage) register_course_button.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" Clicked on Register Course button");
            logger.getLogger().info("Showing Registering Course Page to the Student '"+username+"'");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleter_registered_course_action(ActionEvent event) throws IOException {
        DeleteRegisteredCourseTableViewPaneController ds = new DeleteRegisteredCourseTableViewPaneController();
        ds.openPage(username);
        System.out.println("student user in delete in main: "+username);
        currentStage = (Stage) deleter_registered_course_button.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" Clicked on Delete Registered Course button");
            logger.getLogger().info("Showing Deleting Registered Course Page to the Student'"+username+"'");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void my_schedule_action(ActionEvent event) throws IOException {
        ScheduleController scheuld = new ScheduleController();
        scheuld.openPage(username);
        System.out.println("student user in my schedule in main: "+username);
        currentStage = (Stage) my_schedule_btn.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" Clicked on Showing Schedle Course button");
            logger.getLogger().info("Showing Student Scheuld Page to the Student '"+username+"'");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void reports_action(ActionEvent event) throws IOException {
    }

    @FXML
    private void log_out_action(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("../login/loginPagePane.fxml"));
        stage = new Stage();
        stage.setTitle("Login Page");
        stage.setScene(new Scene(pane));
        stage.show();
        currentStage = (Stage) log_out_btn.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" logged out");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
