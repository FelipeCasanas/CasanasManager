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
                isLogged = true;
            }

            loginPS.close();
            loginResult.close();
            this.closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return isLogged;
    }

    public boolean vehicleStillHere(String plate) {
        this.connect();
        Connection link = this.getConnection();
        boolean stillHere = false;

        try {
            String searchVehicleQuery = "SELECT * FROM vehicle WHERE plate = ? AND checkout_by IS NULL;";
            PreparedStatement searchVehiclePS = link.prepareStatement(searchVehicleQuery);
            searchVehiclePS.setString(1, plate);
            ResultSet foundVehicleRS = searchVehiclePS.executeQuery();

            if (foundVehicleRS.next()) {
                stillHere = true;
            }

            searchVehiclePS.close();
            foundVehicleRS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stillHere;
    }

    public String[] searchVehicle(String searchBy, String search) {
        this.connect();
        Connection link = getConnection();
        String[] vehicleData = new String[11];
        vehicleData[0] = null;

        try {
            String searchVehicleQuery = "SELECT * FROM vehicle WHERE plate = ?;";
            PreparedStatement searchVehiclePS = link.prepareStatement(searchVehicleQuery);
            //searchVehiclePS.setString(1, searchBy);
            searchVehiclePS.setString(1, search);
            ResultSet foundVehicleRS = searchVehiclePS.executeQuery();

            if (foundVehicleRS.next()) {
                vehicleData[0] = foundVehicleRS.getString("id");
                vehicleData[1] = foundVehicleRS.getString("type");
                vehicleData[2] = foundVehicleRS.getString("color");
                vehicleData[3] = foundVehicleRS.getString("state");
                vehicleData[4] = foundVehicleRS.getString("checkout_state");
                vehicleData[5] = foundVehicleRS.getString("checkin_by");
                vehicleData[6] = foundVehicleRS.getString("checkout_by");
                vehicleData[7] = foundVehicleRS.getString("owner_id");
                vehicleData[8] = foundVehicleRS.getString("plate");
                vehicleData[9] = foundVehicleRS.getString("checkin_hour");
                vehicleData[10] = foundVehicleRS.getString("checkout_hour");
            }

            searchVehiclePS.close();
            foundVehicleRS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vehicleData;
    }

    public boolean insertVehicle(String[] vehicleData, int workerId, String formattedDate) {
        this.connect();
        Connection link = this.getConnection();
        boolean inserted = false;

        try {
            String query = "INSERT INTO vehicle(type, color, state, checkin_by, owner_id, plate, checkin_hour) VALUES (?, ?, ? , ?, ?, ?, ?);";
            PreparedStatement insertVehiclePS = link.prepareStatement(query);
            insertVehiclePS.setString(1, vehicleData[0]);
            insertVehiclePS.setString(2, vehicleData[1]);
            insertVehiclePS.setString(3, vehicleData[2]);
            insertVehiclePS.setInt(4, workerId);
            insertVehiclePS.setString(5, vehicleData[3]);
            insertVehiclePS.setString(6, vehicleData[4]);
            insertVehiclePS.setString(7, formattedDate);
            int insertStatus = insertVehiclePS.executeUpdate();

            if (insertStatus == 1) {
                inserted = true;
            }

            link.commit();
            insertVehiclePS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inserted;
    }

    public boolean checkOutVehicle(String vehicleId, String checkoutState, String checkoutBy, String checkoutHour, Double parkingPrice) throws SQLException {
        //Se conecta a base de datos
        this.connect();
        Connection link = getConnection();
        boolean checkout = false;

        try {
            //Se prepara la sentencia, se asigna valores y ejecuta para dar salida
            String checkoutQuery = "UPDATE vehicle SET checkout_state = ?, checkout_by = ?, checkout_hour = ? WHERE id = ?";
            PreparedStatement checkoutPS = link.prepareStatement(checkoutQuery);
            checkoutPS.setString(1, checkoutState);
            checkoutPS.setString(2, checkoutBy);
            checkoutPS.setString(3, checkoutHour);
            checkoutPS.setString(4, vehicleId);
            int checkoutExecuted = checkoutPS.executeUpdate();

            //Se prepara la sentencia, se asigna valores y se ejecuta para establecer el pago
            String checkoutPaymentQuery = "INSERT INTO income(vehicle_id, pay_amount) VALUES(?, ?);";
            PreparedStatement checkoutPaymentPS = link.prepareStatement(checkoutPaymentQuery);
            checkoutPaymentPS.setString(1, vehicleId);
            checkoutPaymentPS.setDouble(2, parkingPrice);
            int checkoutPaymentExecuted = checkoutPaymentPS.executeUpdate();

            //Valida si se hizo la actualizacion
            if (checkoutExecuted == 1 && checkoutPaymentExecuted == 1) {
                checkout = true;
                link.commit();
            } else {
                link.rollback();
            }

            checkoutPS.close();
            checkoutPaymentPS.close();
        } catch (SQLException ex) {
            link.rollback();
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }

        return checkout;
    }
}
