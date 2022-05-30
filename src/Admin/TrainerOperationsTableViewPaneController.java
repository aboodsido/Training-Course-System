/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Admin;

import Classes.Trainer;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import login.LoginPagePaneController;

/**
 * FXML Controller class
 *
 * @author Sword
 */
public class TrainerOperationsTableViewPaneController implements Initializable {

    @FXML
    private Label trainer_id_label;
    @FXML
    private Label trainer_name_label;
    @FXML
    private Label trainer_major_label;
    @FXML
    private Label trainer_age_label;
    @FXML
    private TextField trainer_id_field;
    @FXML
    private TextField trainer_name_field;
    @FXML
    private TextField trainer_major_field;
    @FXML
    private TextField trainer_age_field;
    @FXML
    private Label trainer_phone_label;
    @FXML
    private Label trainer_address_label;
    @FXML
    private TextField trainer_phone_field;
    @FXML
    private TextField trainer_address_field;
    @FXML
    private TableView<Trainer> trainer_tableView;
    @FXML
    private TableColumn<Trainer, String> trainer_id_column;
    @FXML
    private TableColumn<Trainer, String> trainer_name_column;
    @FXML
    private TableColumn<Trainer, String> trainer_major_column;
    @FXML
    private TableColumn<Trainer, Integer> trainer_age_column;
    @FXML
    private TableColumn<Trainer, Integer> trainer_phone_column;
    @FXML
    private TableColumn<Trainer, String> trainer_address_column;
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

    Parent pane;
    Stage stage;
    Stage currentStage;

    Connection connection;
    ResultSet rs;

    ObservableList<Trainer> trainers = FXCollections.observableArrayList();
    @FXML
    private Label password_label;
    @FXML
    private TextField password_field;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aam_training_center_db", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("successful connection to database");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPagePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

        trainer_id_column.setCellValueFactory(new PropertyValueFactory("trainer_user"));
        trainer_name_column.setCellValueFactory(new PropertyValueFactory("name"));
        trainer_major_column.setCellValueFactory(new PropertyValueFactory("major"));
        trainer_age_column.setCellValueFactory(new PropertyValueFactory("age"));
        trainer_phone_column.setCellValueFactory(new PropertyValueFactory("phone_number"));
        trainer_address_column.setCellValueFactory(new PropertyValueFactory("address"));

        trainer_tableView.getSelectionModel().selectedIndexProperty().addListener(e -> {
            this.resetTrainerTextFields();
            this.showSelectedTrainer();
        });

    }

    @FXML
    private void show_action(ActionEvent event) throws SQLException {
        this.showAllTrainers();
    }

    @FXML
    private void add_action(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        try {

            Statement s = connection.createStatement();

            String id = trainer_id_field.getText();
            String name = trainer_name_field.getText();
            String major = trainer_major_field.getText();
            Integer age = Integer.parseInt(trainer_age_field.getText());
            Integer phone_number = Integer.parseInt(trainer_phone_field.getText());
            String address = trainer_address_field.getText();
            String password = MD5.MD5EncryptingPassword.encryptingPassword(password_field.getText());
            System.out.println("i am out of if else");
            if (id.isEmpty() || name.isEmpty() || major.isEmpty() || age == 0 || phone_number == 0 || address.isEmpty() || password.isEmpty()) {
                System.out.println("I am in if else");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Trainer Add Error:");
                alert.setHeaderText("Empty text fields");
                alert.setContentText("sorry, u didnt filled all student inforamtion text fields. Please fill it and try to add student again.");
                alert.showAndWait();
            }

            String sql = "INSERT INTO trainer VALUES ('" + id + "', '" + name + "','" + age + "'," + phone_number + ", '" + address + "','" + major + "');";
            System.out.println(sql);
            s.executeUpdate(sql);
            String sql2 = "INSERT INTO login_info VALUES ('" + id + "', '" + password + "', 'trainer');";
            s.executeUpdate(sql2);
            this.showAllTrainers();

        } catch (NumberFormatException n) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Trainer Add Error:");
            alert.setHeaderText("Number format error");
            alert.setContentText("sorry, u filled student inforamtion text fields of number format with Alphapitical format. Please fill it correctly and then try again.");
            alert.showAndWait();

        }
    }

    @FXML
    private void edit_action(ActionEvent event) throws SQLException {
        try {

            Trainer trainer = trainer_tableView.getSelectionModel().getSelectedItem();
            String old_username = trainer.getTrainer_user();
            System.out.println(old_username);

            Statement s = connection.createStatement();

            String id = trainer_id_field.getText();
            String name = trainer_name_field.getText();
            String major = trainer_major_field.getText();
            Integer age = Integer.parseInt(trainer_age_field.getText());
            Integer phone_number = Integer.parseInt(trainer_phone_field.getText());
            String address = trainer_address_field.getText();

            String sql = "UPDATE trainer SET trainer_user='" + id + "', name ='" + name + "',age= '" + age + "',phone_number= " + phone_number + ", address = '" + address + "',major= '" + major + "' WHERE trainer_user = '" + old_username + "';";
            String sql2 = "UPDATE login_info SET username = '" + id + "' WHERE username = '" + old_username + "';";
            System.out.println(sql);
            System.out.println(sql2);
            s.executeUpdate(sql);
            s.executeUpdate(sql2);
            this.showAllTrainers();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Trainer Edit Error:");
            alert.setHeaderText("No Selected Trainer");
            alert.setContentText("SORRY,  u didnt select any trainer to edit. Please select trainer from the table and then edit trainer");
            alert.showAndWait();
        }
    }

    @FXML
    private void delete_action(ActionEvent event) throws SQLException {
        Statement s = connection.createStatement();
        String id = trainer_id_field.getText();
        String sql = "DELETE FROM trainer WHERE trainer_user = '" + id + "';";
        String sql2 = "DELETE FROM login_info WHERE username = '"+id+"';";
        s.executeUpdate(sql);
        s.executeUpdate(sql2);
        this.resetTrainerTextFields();
        this.showAllTrainers();
    }

    @FXML
    private void reset_action(ActionEvent event) {
        this.resetTrainerTextFields();

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
        System.out.println("Closing Trainer Operations Page and back to Admin Main Page");
    }

    private void showSelectedTrainer() {
        Trainer trainer = trainer_tableView.getSelectionModel().getSelectedItem();
        if (trainer != null) {
            trainer_id_field.setText(trainer.getTrainer_user());
            trainer_name_field.setText(trainer.getName());
            trainer_major_field.setText(trainer.getMajor());
            trainer_age_field.setText(String.valueOf(trainer.getAge()));
            trainer_phone_field.setText(String.valueOf(trainer.getPhone_number()));
            trainer_address_field.setText(trainer.getAddress());
        }
    }

    public void showAllTrainers() throws SQLException {
        trainers.clear();
        Statement s = connection.createStatement();
        rs = s.executeQuery("Select * From trainer");
        while (rs.next()) {
            Trainer trainer = new Trainer();
            trainer.setTrainer_user(rs.getString("trainer_user"));
            trainer.setName(rs.getString("name"));
            trainer.setMajor(rs.getString("major"));
            trainer.setAge(rs.getInt("age"));
            trainer.setPhone_number(rs.getInt("phone_number"));
            trainer.setAddress(rs.getString("address"));
            trainers.add(trainer);
        }
        trainer_tableView.setItems(trainers);
    }

    private void resetTrainerTextFields() {
        trainer_id_field.setText("");
        trainer_name_field.setText("");
        trainer_major_field.setText("");
        trainer_age_field.setText("");
        trainer_phone_field.setText("");
        trainer_address_field.setText("");
        password_field.setText("");
    }
}
