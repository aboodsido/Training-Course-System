/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Admin;

import Classes.Room;
import Classes.Trainer;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class RoomOperationsTableViewPaneController implements Initializable {

    @FXML
    private Label room_id_label;
    @FXML
    private TextField room_id_field;
    @FXML
    private TextArea textArea;
    @FXML
    private TableColumn<Room, String> room_id_column;
    @FXML
    private Button room_show_button;
    @FXML
    private Button room_add_button;
    @FXML
    private Button room_edit_button;
    @FXML
    private Button room_delete_button;
    @FXML
    private Button room_reset_button;
    @FXML
    private Button btnBackToMain;
    @FXML
    private TableView<Room> room_tableView;

    Parent pane;
    Stage stage;
    Stage currentStage;

    Connection connection;
    ResultSet rs;

    ObservableList<Room> rooms = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aam_training_center_db", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("successful connection to database");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPagePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

        room_id_column.setCellValueFactory(new PropertyValueFactory("room_id"));

        room_tableView.getSelectionModel().selectedIndexProperty().addListener(e -> {
            this.showSelectedRoom();
        });
    }

    @FXML
    private void room_show_action(ActionEvent event) throws SQLException {
        this.showAllRooms();
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Shows all Rooms");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void room_add_action(ActionEvent event) throws SQLException {
        try {

            Statement s = connection.createStatement();

            String roomId = room_id_field.getText();

            String sql = "INSERT INTO room VALUES ('" + roomId + "');";
            s.executeUpdate(sql);
            this.showAllRooms();
            try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Admin Added Room");
            logger.getLogger().info("QUERY: "+sql);
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Room Add Error:");
            alert.setHeaderText("Empty text fields");
            alert.setContentText("sorry, u didnt filled room inforamtion text fields. Please fill it and try to add student room.");
            alert.showAndWait();
            try {
            SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info("couldn't add room because room inforamtion text fields");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    @FXML
    private void room_edit_action(ActionEvent event) throws SQLException {
        try {
            Room room = room_tableView.getSelectionModel().getSelectedItem();
            String old_roomId = room.getRoom_id();

            Statement s = connection.createStatement();

            String id = room_id_field.getText();

            String sql = "UPDATE room SET room_id='" + id + "' WHERE room_id = '" + old_roomId + "';";
            System.out.println(sql);
            s.executeUpdate(sql);
            this.showAllRooms();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(" Edit a course its id: " + old_roomId);
                logger.getLogger().info("QUERY: " + sql);
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Room Edit Error:");
            alert.setHeaderText("No Selected Room");
            alert.setContentText("SORRY,  You didnt select any Room to edit. Please select Room from the table and then edit Room");
            alert.showAndWait();
            try {
                SystemLogger logger = SystemLogger.getInstance();
                logger.getLogger().info(" couldn't edit a room because there is no selected course");
            } catch (IOException ex) {
                Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void room_delete_acton(ActionEvent event) throws SQLException {
        Statement s = connection.createStatement();
        String id = room_id_field.getText();

        String sql = "DELETE FROM room WHERE room_id = '" + id + "';";
        System.out.println(sql);
        s.executeUpdate(sql);
        this.resetRoomTextFields();
        this.showAllRooms();

    }

    @FXML
    private void room_reset_action(ActionEvent event) {
        this.resetRoomTextFields();
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
        System.out.println("Closing Room Operations Page and back to Admin Main Page");
        try {
            SystemLogger logger = SystemLogger.getInstance();
            logger.getLogger().info("Back to Admin Main Page");
        } catch (IOException ex) {
            Logger.getLogger(StudentMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSelectedRoom() {
        Room room = room_tableView.getSelectionModel().getSelectedItem();
        if (room != null) {
            room_id_field.setText(room.getRoom_id());

        }
    }

    public void showAllRooms() throws SQLException {
        rooms.clear();
        Statement s = connection.createStatement();
        rs = s.executeQuery("Select * From room");
        while (rs.next()) {
            Room room = new Room();
            room.setRoom_id(rs.getString("room_id"));

            rooms.add(room);
        }
        room_tableView.setItems(rooms);
    }

    private void resetRoomTextFields() {
        room_id_field.setText("");

    }
}
