/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Felipe
 */
public class Connect {
    private Connection link;
    
    public Connect() {
        link = null;
        connect();
    }
    
    public void connect() {
        String dbUrl = "jdbc:mysql://localhost:3306/parking_managment", dbUser = "root", dbPassword = "";
        
        try {
            link = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            link.setAutoCommit(false);
            
            if(link != null) {
                System.out.println("Connection successful");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public Connection getConnection() {
        return link;
    }
    
    public void closeConnection() throws SQLException {
        link.close();
        link = null;
        
        if(link == null) {
            System.out.println("the connection has been closed");
        }
    }
}
