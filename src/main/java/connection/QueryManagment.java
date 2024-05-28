/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import managmentCore.UserManagment;

/**
 *
 * @author Felipe
 */
public class QueryManagment extends Connect {

    public void getUserData(ResultSet loginResult) throws SQLException {
        UserManagment.setId(Integer.parseInt(loginResult.getString("user_id")));
        UserManagment.setCard_id(loginResult.getString("card_id"));
        UserManagment.setName(loginResult.getString("name"));
        UserManagment.setLastName(loginResult.getString("last_name"));
        UserManagment.setBirth_day(loginResult.getString("birth_day"));
        UserManagment.setCategory(Integer.parseInt(loginResult.getString("category")));
        UserManagment.setEmail(loginResult.getString("email"));
        UserManagment.setPassword(loginResult.getString("password"));
        UserManagment.setActive(Integer.parseInt(loginResult.getString("active")));
        UserManagment.setAdmin(Integer.parseInt(loginResult.getString("admin")));
    }

    public boolean loginQuery(Component component, String email, String password) {
        this.connect();
        Connection link = this.getConnection();
        boolean isLogged = false;

        try {
            String query = "SELECT * from user WHERE email = ? AND password = ? AND active = ?;";
            PreparedStatement loginPS = link.prepareStatement(query);
            loginPS.setString(1, email);
            loginPS.setString(2, password);
            loginPS.setString(3, "1");
            ResultSet loginResult = loginPS.executeQuery();

            if (loginResult.next()) {
                getUserData(loginResult);
                loginPS.close();
                loginResult.close();
                this.closeConnection();
                isLogged = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return isLogged;
    }
    
    public boolean vehicleStillHere(String plate) {
        this.connect();
        Connection link = this.getConnection();
        boolean isHere = false;

        try {
            String searchVehicleQuery = "SELECT * FROM vehicle WHERE plate = ? AND checkout_by = ?;";
            PreparedStatement searchVehiclePS = link.prepareStatement(searchVehicleQuery);
            searchVehiclePS.setString(1, plate);
            searchVehiclePS.setString(2, null);
            ResultSet foundVehicleRS = searchVehiclePS.executeQuery();
            
            if(foundVehicleRS.next()) {
                isHere = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isHere;
    }
    
    public boolean insertVehicle(String [] vehicleData, int UserId, String formattedDate) {
        this.connect();
        Connection link = this.getConnection();
        boolean inserted = false;
        
        try {
            String query = "INSERT INTO vehicle(type, color, state, checkin_by, owner_id, plate, checkin_hour) VALUES (?, ?, ? , ?, ?, ?, ?);";
            PreparedStatement insertVehiclePS = link.prepareStatement(query);
            insertVehiclePS.setString(1, vehicleData[0]);
            insertVehiclePS.setString(2, vehicleData[1]);
            insertVehiclePS.setString(3, vehicleData[2]);
            insertVehiclePS.setInt(4, UserId);
            insertVehiclePS.setString(5, vehicleData[3]);
            insertVehiclePS.setString(6, vehicleData[4]);
            insertVehiclePS.setString(7, formattedDate);
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return inserted;
    }

}
