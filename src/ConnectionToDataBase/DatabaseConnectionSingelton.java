package ConnectionToDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Sword
 */
public class DatabaseConnectionSingelton {

    private static DatabaseConnectionSingelton instance = null;

    public static DatabaseConnectionSingelton getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DatabaseConnectionSingelton();
        }
        return instance;
    }

    private DatabaseConnectionSingelton() {
    }
    
    public Connection getConnection () throws SQLException, ClassNotFoundException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aam_training_center_db", "root", "");
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("successful connection to database");
        return connection;
    }

}
