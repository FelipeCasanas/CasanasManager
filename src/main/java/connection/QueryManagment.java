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
            String query = "SELECT * from my_user WHERE email = ? AND password = ? AND active = ?;";
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
            String searchVehicleQuery = "SELECT * FROM vehicle WHERE plate = ? AND checkout_hour IS NULL;";
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
            String searchVehicleQuery = "SELECT * FROM vehicle v INNER JOIN vehicle_type vt ON v.type = vt.type_id INNER JOIN vehicle_state vs ON v.state = vs.id WHERE plate = ?;";
            PreparedStatement searchVehiclePS = link.prepareStatement(searchVehicleQuery);
            //searchVehiclePS.setString(1, searchBy);
            searchVehiclePS.setString(1, search);
            ResultSet foundVehicleRS = searchVehiclePS.executeQuery();

            if (foundVehicleRS.next()) {
                vehicleData[0] = foundVehicleRS.getString("id");
                vehicleData[1] = foundVehicleRS.getString("type_name");
                vehicleData[2] = foundVehicleRS.getString("color");
                vehicleData[3] = foundVehicleRS.getString("vs.state");
                vehicleData[4] = foundVehicleRS.getString("vs.checkout_state");
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
            String query = "INSERT INTO vehicle(type, color, state, checkout_state, checkin_by, checkout_by, owner_id, plate, checkin_hour) VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?);";
            PreparedStatement insertVehiclePS = link.prepareStatement(query);
            insertVehiclePS.setString(1, vehicleData[0]);
            insertVehiclePS.setString(2, vehicleData[1]);
            insertVehiclePS.setString(3, vehicleData[2]);
            insertVehiclePS.setInt(4, 0);
            insertVehiclePS.setInt(5, workerId);
            insertVehiclePS.setInt(6, 0);
            insertVehiclePS.setString(7, vehicleData[3]);
            insertVehiclePS.setString(8, vehicleData[4]);
            insertVehiclePS.setString(9, formattedDate);
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

    public String[] queryRates() {
        this.connect();
        Connection link = getConnection();
        String[] rates = new String[3];

        try {
            String ratesQuery = "SELECT * FROM rate LIMIT 3;";
            PreparedStatement ratesPS = link.prepareStatement(ratesQuery);
            ResultSet ratesRS = ratesPS.executeQuery();

            int index = 0; // Índice para el array
            while (ratesRS.next() && index < 3) { // Asegurarse de que no se sobrepase el límite de 3
                rates[index] = ratesRS.getString("rate_amount");
                index++;
            }

            ratesPS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rates;
    }

    public boolean updateRates(double amount, int vehicleCode) throws SQLException {
        this.connect();
        Connection link = getConnection();
        boolean updated = false;

        try {
            String updateRateQuery = "UPDATE rate SET rate_amount = ? WHERE rates_id = ?";
            PreparedStatement updateRatePS = link.prepareStatement(updateRateQuery);
            updateRatePS.setDouble(1, amount);
            updateRatePS.setInt(2, vehicleCode);
            int executedUpdate = updateRatePS.executeUpdate();

            if (executedUpdate == 1) {
                updated = true;
                link.commit();
            } else {
                link.rollback();
            }

            updateRatePS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            link.rollback();
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }

    public String[][] queryLogs(Boolean[] willBeUpdated, String[] fields) {
        this.connect();
        Connection link = getConnection();

        //searchLogsQuery se toma como consulta base, esta lo que hace es obtener los valores para cada columna y esto lo repite en cada fila
        String searchLogsQuery = "SELECT v1.*, vt1.type_name, c1.color_name, vs1.state AS state_name, vs2.state AS checkout_state_name, u1.name AS checkin_by_user_name, u2.name AS checkout_by_user_name "
                        + "FROM vehicle v1 INNER JOIN vehicle_type vt1 ON v1.type = vt1.type_id "
                        + "INNER JOIN color c1 ON v1.color = c1.id "
                        + "INNER JOIN vehicle_state vs1 ON v1.state = vs1.id "
                        + "INNER JOIN vehicle_state vs2 ON v1.checkout_state = vs2.id "
                        + "INNER JOIN my_user u1 ON v1.checkin_by = u1.user_id "
                        + "INNER JOIN my_user u2 ON v1.checkout_by = u2.user_id", whereQuery = "", orderByQuery = " ORDER BY v1.id ASC";
        
        //Se pregunta que condiciones de busqueda se van a aplicar a la consulta
        String caseKey = (willBeUpdated[0] ? "1" : "0")
                + (willBeUpdated[1] ? "1" : "0")
                + (willBeUpdated[2] ? "1" : "0");

        //Se evalua la cadena de caracteres y se guarda la consulta del case que sea verdadera 
        switch (caseKey) {
            case "111":
                whereQuery = "SELECT * FROM vehicle WHERE type = " + fields[0] + " AND state = " + fields[1] + " AND checkout_by = " + fields[2];
                break;
            case "110":
                whereQuery = "SELECT * FROM vehicle WHERE type = " + fields[0] + " AND state = " + fields[1];
                break;
            case "011":
                whereQuery = "SELECT * FROM vehicle WHERE state = " + fields[1] + " AND checkout_by = " + fields[2];
                break;
            case "100":
                whereQuery = "SELECT * FROM vehicle WHERE type = " + fields[0];
                break;
            case "010":
                whereQuery = "SELECT * FROM vehicle WHERE state = " + fields[1];
                break;
            case "001":
                whereQuery = "SELECT * FROM vehicle WHERE checkout_by = " + fields[2];
                break;
            default:
                whereQuery = searchLogsQuery + orderByQuery;
                break;
        }

        try {
            int totalRows = 0, range = 0;

            // Se prepara la consulta y se ejecuta
            PreparedStatement searchLogsPS = link.prepareStatement(searchLogsQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet searchLogsRS = searchLogsPS.executeQuery();

            // Contar el número total de filas
            while (searchLogsRS.next()) {
                totalRows++;
            }

            // Si existe al menos una fila se guarda la información en la matriz
            if (totalRows > 0) {
                // Instancia matriz que guarda los registros
                String[][] logsData = new String[totalRows][11];

                // Mover el puntero al inicio del ResultSet
                searchLogsRS.beforeFirst();

                // Llenar la matriz con los datos
                while (searchLogsRS.next()) {
                    logsData[range][0] = searchLogsRS.getString("id");
                    logsData[range][1] = searchLogsRS.getString("vt1.type_name");
                    logsData[range][2] = searchLogsRS.getString("c1.color_name");
                    logsData[range][3] = searchLogsRS.getString("state_name");
                    logsData[range][4] = searchLogsRS.getString("checkout_state_name");
                    logsData[range][5] = searchLogsRS.getString("checkin_by_user_name");
                    logsData[range][6] = searchLogsRS.getString("checkout_by_user_name");
                    logsData[range][7] = searchLogsRS.getString("owner_id");
                    logsData[range][8] = searchLogsRS.getString("plate");
                    logsData[range][9] = searchLogsRS.getString("checkin_hour");
                    logsData[range][10] = searchLogsRS.getString("checkout_hour");

                    range++;
                }

                searchLogsPS.close();
                searchLogsRS.close();
                return logsData;
            }

            searchLogsPS.close();
            searchLogsRS.close();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

}
