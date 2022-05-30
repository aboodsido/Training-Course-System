package trainer;

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logger.SystemLogger;

public class TrainerPaneController implements Initializable {

    static String username;

    public void openPage(String admin_user) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Trainer Main Page");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("TrainerPane.fxml"))));

        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();

        System.out.println(admin_user + " is loged in");
        username = admin_user;
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" is logged in");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private Button add_new_course;

    @FXML
    private Button delete_student;

    @FXML
    private Button logout;

    @FXML
    private Button reports;

    @FXML
    private Button schedule;

    @FXML
    private Label trainer_label;

    @FXML
    void add_new_course_action(ActionEvent event) throws IOException {

        AddNewCourseToTrainerController tc = new AddNewCourseToTrainerController();
        tc.openPage(username);
        System.out.println("student user in register in main: " + username);
        Stage currentStage = (Stage) add_new_course.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" Clicked on add new course button");
            logger.getLogger().info("Showing Add New Course Page to the Trainer '"+username+"'");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void delete_student_action(ActionEvent event) throws IOException {
        deleteStudentController ds = new deleteStudentController();
        ds.openPage(username);
        System.out.println("trainer user in register in main: " + username);
        Stage currentStage = (Stage) add_new_course.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Showing Delete Student Page to the Trainer '"+username+"'");
            logger.getLogger().info(username+" Clicked on delete student button");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void reports_action(ActionEvent event) {

    }

    @FXML
    void schedule_action(ActionEvent event) throws IOException {
        TrainerScheduleController ts = new TrainerScheduleController();
        ts.openPage(username);
        System.out.println("trainer user in register in main: " + username);
        Stage currentStage = (Stage) add_new_course.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Showing Trainer Scheuld Page to the Trainer '"+username+"'");
            logger.getLogger().info(username+" Clicked on Showing Schedule button");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void logout_action(ActionEvent event) throws IOException {
        
        Pane pane = FXMLLoader.load(getClass().getResource("../login/loginPagePane.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login Page");
        stage.setScene(new Scene(pane));
        stage.show();
        Stage currentStage = (Stage) logout.getScene().getWindow();
        currentStage.close();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info(username+" Logged out");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
