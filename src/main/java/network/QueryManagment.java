/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import corePackage.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import generalUtility.TimeMethods;

/**
 *
 * @author Felipe
 */
public class QueryManagment extends Connect {

    public boolean vehicleStillHere(String plate) {
        this.connect();
        Connection link = this.getConnection();
        boolean stillHere = false;

        try {
            String searchVehicleQuery = "SELECT * FROM item WHERE item_identifiquer = ? AND checkout_hour IS NULL;";
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
            String searchVehicleQuery = "SELECT i1.id, c1.color_name, s1.state_name AS checkin_state_name, s2.state_name AS checkout_state_name, "
                    + "u1.name AS checkin_by_user_name, u2.name AS checkout_by_user_name, "
                    + "b1.name AS business_name, i1.item_identifiquer, i1.checkin_hour, i1.checkout_hour "
                    + "FROM item i1 "
                    + "INNER JOIN color c1 ON i1.color = c1.id "
                    + "INNER JOIN state s1 ON i1.checkin_state = s1.id "
                    + "LEFT JOIN state s2 ON i1.checkout_state = s2.id "
                    + "INNER JOIN my_user u1 ON i1.checkin_by = u1.id "
                    + "LEFT JOIN my_user u2 ON i1.checkout_by = u2.id "
                    + "INNER JOIN business b1 ON i1.business_id = b1.id "
                    + "WHERE i1.item_identifiquer = ?";

            PreparedStatement searchVehiclePS = link.prepareStatement(searchVehicleQuery);
            searchVehiclePS.setString(1, search);
            ResultSet foundVehicleRS = searchVehiclePS.executeQuery();

            if (foundVehicleRS.next()) {
                vehicleData[0] = foundVehicleRS.getString("id");
                vehicleData[1] = foundVehicleRS.getString("color_name");
                vehicleData[2] = foundVehicleRS.getString("checkin_state_name");
                vehicleData[3] = foundVehicleRS.getString("checkout_state_name");
                vehicleData[4] = foundVehicleRS.getString("checkin_by_user_name");
                vehicleData[5] = foundVehicleRS.getString("checkout_by_user_name");
                vehicleData[6] = foundVehicleRS.getString("business_name");
                vehicleData[7] = foundVehicleRS.getString("item_identifiquer");
                vehicleData[8] = foundVehicleRS.getString("checkin_hour");
                vehicleData[9] = foundVehicleRS.getString("checkout_hour");
            }

            searchVehiclePS.close();
            foundVehicleRS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vehicleData;
    }

    public boolean insertVehicle(String[] vehicleData, String workerId, String formattedDate) {
        this.connect();
        Connection link = this.getConnection();
        boolean inserted = false;

        try {
            User user = new User();

            String query = "INSERT INTO item(item_identifiquer, type, color, checkin_state, checkout_state, checkin_by, checkout_by, business_id, checkin_hour, checkout_hour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement insertVehiclePS = link.prepareStatement(query);
            insertVehiclePS.setString(1, vehicleData[4]);  // item_identifiquer
            insertVehiclePS.setString(2, vehicleData[0]);  // type
            insertVehiclePS.setString(3, vehicleData[1]);  // color
            insertVehiclePS.setString(4, vehicleData[2]);  // checkin_state
            insertVehiclePS.setInt(5, 0);                  // checkout_state (inicialmente 0)
            insertVehiclePS.setString(6, workerId);        // checkin_by
            insertVehiclePS.setInt(7, 0);                  // checkout_by (inicialmente 0)
            insertVehiclePS.setString(8, user.getBusiness_id());  // business_id
            insertVehiclePS.setString(9, formattedDate);   // checkin_hour
            insertVehiclePS.setString(10, null);           // checkout_hour (inicialmente null)
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

    public boolean checkOutVehicle(String vehicleId, String businessId, String checkoutState, String checkoutBy, String checkoutHour, Double parkingPrice) throws SQLException {
        //Se conecta a base de datos
        this.connect();
        Connection link = getConnection();
        boolean checkout = false;

        try {
            // Se prepara la sentencia, se asignan valores y se ejecuta para dar salida
            String checkoutQuery = "UPDATE item SET checkout_state = ?, checkout_by = ?, checkout_hour = ? WHERE id = ?";
            PreparedStatement checkoutPS = link.prepareStatement(checkoutQuery);
            checkoutPS.setString(1, checkoutState);    // Estado de salida (checkout_state)
            checkoutPS.setString(2, checkoutBy);       // Usuario que realiza el checkout (checkout_by)
            checkoutPS.setString(3, checkoutHour);     // Hora de salida (checkout_hour)
            checkoutPS.setString(4, vehicleId);           // ID del ítem
            int checkoutExecuted = checkoutPS.executeUpdate();

            // Se prepara la sentencia, se asignan valores y se ejecuta para establecer el pago
            String checkoutPaymentQuery = "INSERT INTO `income`(`business_id`, `item_id`, `rate_amount`) VALUES (?, ?, ?)";
            PreparedStatement checkoutPaymentPS = link.prepareStatement(checkoutPaymentQuery);
            checkoutPaymentPS.setString(1, businessId);
            checkoutPaymentPS.setString(2, vehicleId);     // ID del ítem (antes vehicle_id)
            checkoutPaymentPS.setDouble(3, parkingPrice); // Monto del pago (pay_amount)
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

    public int[] countLogs(String dateToSearch) {
        this.connect();
        Connection link = getConnection();

        int[] totalVehiclesCount = new int[3];

        TimeMethods formatter = new TimeMethods();
        String dayStart = formatter.formatDate() + " 00:00:00";
        String dayEnd = formatter.formatDate() + " 23:59:59";

        String query = "SELECT ct.business_type FROM item i "
                + "INNER JOIN category ct ON i.business_id = ct.id "
                + "WHERE i.checkin_hour BETWEEN ? AND ?";

        try {
            PreparedStatement ps = link.prepareStatement(query);
            ps.setString(1, dayStart);
            ps.setString(2, dayEnd);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String businessType = rs.getString("business_type");

                if (businessType.equals("carro")) {
                    totalVehiclesCount[0]++;
                } else if (businessType.equals("moto")) {
                    totalVehiclesCount[1]++;
                } else if (businessType.equals("bicicleta")) {
                    totalVehiclesCount[2]++;
                }
            }

            return totalVehiclesCount;

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String[][] queryLogs(Boolean[] willBeUpdated, String[] fields) {
        this.connect();
        Connection link = getConnection();

        // Actualizar la consulta SQL para la nueva base de datos
        String searchLogsQuery = "SELECT i1.*, c1.color_name, s1.state_name AS checkin_state_name, s2.state_name AS checkout_state_name, "
                + "u1.name AS checkin_by_user_name, u2.name AS checkout_by_user_name, p1.rate_amount "
                + "FROM item i1 "
                + "INNER JOIN color c1 ON i1.color = c1.id "
                + "INNER JOIN state s1 ON i1.checkin_state = s1.id "
                + "INNER JOIN state s2 ON i1.checkout_state = s2.id "
                + "INNER JOIN my_user u1 ON i1.checkin_by = u1.id "
                + "INNER JOIN my_user u2 ON i1.checkout_by = u2.id "
                + "INNER JOIN price p1 ON i1.business_id = p1.business_id", whereQuery = "", orderByQuery = " ORDER BY i1.id ASC";

        // Se pregunta qué condiciones de búsqueda se van a aplicar a la consulta
        String caseKey = (willBeUpdated[0] ? "1" : "0")
                + (willBeUpdated[1] ? "1" : "0")
                + (willBeUpdated[2] ? "1" : "0");

        // Se evalúa la cadena de caracteres y se guarda la consulta del case que sea verdadera 
        switch (caseKey) {
            case "111":
                whereQuery = " WHERE i1.item_identifiquer = " + fields[0] + " AND i1.checkin_state = " + fields[1] + " AND i1.checkout_by = " + fields[2];
                searchLogsQuery += whereQuery;
                break;
            case "110":
                whereQuery = " WHERE i1.item_identifiquer = " + fields[0] + " AND i1.checkin_state = " + fields[1];
                searchLogsQuery += whereQuery;
                break;
            case "011":
                whereQuery = " WHERE i1.checkin_state = " + fields[1] + " AND i1.checkout_by = " + fields[2];
                searchLogsQuery += whereQuery;
                break;
            case "101":
                whereQuery = " WHERE i1.item_identifiquer = " + fields[0] + " AND i1.checkout_by = " + fields[2];
                searchLogsQuery += whereQuery;
                break;
            case "100":
                whereQuery = " WHERE i1.item_identifiquer = " + fields[0];
                searchLogsQuery += whereQuery;
                break;
            case "010":
                whereQuery = " WHERE i1.checkin_state = " + fields[1];
                searchLogsQuery += whereQuery;
                break;
            case "001":
                whereQuery = " WHERE i1.checkout_by = " + fields[2];
                searchLogsQuery += whereQuery;
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
                String[][] logsData = new String[totalRows][12];

                // Mover el puntero al inicio del ResultSet
                searchLogsRS.beforeFirst();

                // Llenar la matriz con los datos
                while (searchLogsRS.next()) {
                    logsData[range][0] = searchLogsRS.getString("id");
                    logsData[range][1] = searchLogsRS.getString("color_name");
                    logsData[range][2] = searchLogsRS.getString("checkin_state_name");
                    logsData[range][3] = searchLogsRS.getString("checkout_state_name");
                    logsData[range][4] = searchLogsRS.getString("checkin_by_user_name");
                    logsData[range][5] = searchLogsRS.getString("checkout_by_user_name");
                    logsData[range][6] = searchLogsRS.getString("owner_id");
                    logsData[range][7] = searchLogsRS.getString("item_identifiquer");
                    logsData[range][8] = searchLogsRS.getString("checkin_hour");
                    logsData[range][9] = searchLogsRS.getString("checkout_hour");
                    logsData[range][10] = searchLogsRS.getString("pay_amount");
                    logsData[range][11] = searchLogsRS.getString("rate_amount");

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

    public String[][] getCheckoutData(String dateToSearch) {
        this.connect();
        Connection link = getConnection();

        // Actualizar la consulta SQL para la nueva base de datos
        String innerJoinQuery = " INNER JOIN category c1 ON i1.item_identifiquer = c1.id "
                + "INNER JOIN my_user mu ON i1.checkout_by = mu.id";
        String whereQuery = " WHERE i1.checkout_hour BETWEEN '" + dateToSearch + " 00:00:00' AND '" + dateToSearch + " 23:59:59'";
        String checkoutCountQuery = "SELECT i1.id, c1.business_type AS type_name, i1.checkin_hour AS pay_amount, mu.name, mu.last_name "
                + "FROM item i1" + innerJoinQuery + whereQuery;

        String[][] checkoutData = null;
        PreparedStatement checkoutCountPS = null;
        ResultSet checkoutCountRS = null;

        try {
            // Prepara la consulta
            checkoutCountPS = link.prepareStatement(checkoutCountQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            checkoutCountRS = checkoutCountPS.executeQuery();

            // Contar las filas
            checkoutCountRS.last();
            int totalRows = checkoutCountRS.getRow();

            // Si hay filas, inicializa el arreglo
            if (totalRows > 0) {
                checkoutData = new String[totalRows][5];
                checkoutCountRS.beforeFirst();

                int range = 0;
                while (checkoutCountRS.next()) {
                    checkoutData[range][0] = checkoutCountRS.getString("id");
                    checkoutData[range][1] = checkoutCountRS.getString("type_name");
                    checkoutData[range][2] = checkoutCountRS.getString("pay_amount");
                    checkoutData[range][3] = checkoutCountRS.getString("name");
                    checkoutData[range][4] = checkoutCountRS.getString("last_name");
                    range++;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Asegúrate de cerrar los recursos
            try {
                if (checkoutCountRS != null) {
                    checkoutCountRS.close();
                }
                if (checkoutCountPS != null) {
                    checkoutCountPS.close();
                }
                if (link != null) {
                    link.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return checkoutData;
    }

}
