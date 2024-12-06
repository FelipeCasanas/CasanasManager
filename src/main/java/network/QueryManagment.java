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
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class QueryManagment extends Connect {

    public boolean itemStillHere(String itemIdentiquer) {
        this.connect();
        Connection link = this.getConnection();
        boolean stillHere = false;

        try {
            String searchItemQuery = "SELECT * FROM item WHERE item_identifiquer = ? AND checkout_hour IS NULL;";
            PreparedStatement searchItemPS = link.prepareStatement(searchItemQuery);
            searchItemPS.setString(1, itemIdentiquer);
            ResultSet foundItemRS = searchItemPS.executeQuery();

            if (foundItemRS.next()) {
                stillHere = true;
            }

            searchItemPS.close();
            foundItemRS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stillHere;
    }

    public String[] searchItem(String searchBy, String search) {
        this.connect();
        Connection link = getConnection();
        String[] itemData = new String[12];

        try {
            String searchItemQuery = "SELECT "
                    + "i1.id, "
                    + "c1.color_name, "
                    + "s1.state_name AS checkin_state_name, "
                    + "s2.state_name AS checkout_state_name, "
                    + "u1.name AS checkin_by_user_name, "
                    + "u2.name AS checkout_by_user_name, "
                    + "b1.name AS business_name, "
                    + "i1.item_identifiquer, "
                    + "i1.client, "
                    + "t1.type_name, "
                    + "i1.checkin_hour, "
                    + "i1.checkout_hour "
                    + "FROM item i1 "
                    + "LEFT JOIN color c1 ON i1.color = c1.id "
                    + "LEFT JOIN state s1 ON i1.checkin_state = s1.id "
                    + "LEFT JOIN state s2 ON i1.checkout_state = s2.id "
                    + "LEFT JOIN my_user u1 ON i1.checkin_by = u1.id "
                    + "LEFT JOIN my_user u2 ON i1.checkout_by = u2.id "
                    + "LEFT JOIN business b1 ON i1.business_id = b1.id "
                    + "LEFT JOIN type t1 ON i1.item_type = t1.id "
                    + "WHERE i1.item_identifiquer = ?"
                    + "ORDER BY i1.id DESC LIMIT 1;";

            PreparedStatement searchItemPS = link.prepareStatement(searchItemQuery);
            searchItemPS.setString(1, search);
            ResultSet foundItemRS = searchItemPS.executeQuery();

            if (foundItemRS.next()) {
                itemData[0] = foundItemRS.getString("id");
                itemData[1] = foundItemRS.getString("color_name");
                itemData[2] = foundItemRS.getString("checkin_state_name");
                itemData[3] = foundItemRS.getString("checkout_state_name");
                itemData[4] = foundItemRS.getString("checkin_by_user_name");
                itemData[5] = foundItemRS.getString("checkout_by_user_name");
                itemData[6] = foundItemRS.getString("business_name");
                itemData[7] = foundItemRS.getString("item_identifiquer");
                itemData[8] = foundItemRS.getString("client");
                itemData[9] = foundItemRS.getString("type_name"); // Ajustado a type_name
                itemData[10] = foundItemRS.getString("checkin_hour");
                itemData[11] = foundItemRS.getString("checkout_hour");
            }
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return itemData;
    }

    public boolean insertItem(String[] itemData, String workerId, String formattedDate) {
        this.connect();
        Connection link = this.getConnection();
        boolean inserted = false;

        try {
            User user = new User();

            String insertItemQuery = "INSERT INTO item(item_identifiquer, item_type, color, client, checkin_state, checkout_state, checkin_by, checkout_by, business_id, checkin_hour, checkout_hour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement insertItemPS = link.prepareStatement(insertItemQuery);
            insertItemPS.setString(1, itemData[4]);  // item_identifiquer
            insertItemPS.setInt(2, Integer.parseInt(itemData[0]));  // type
            insertItemPS.setString(3, itemData[1]);  // color
            insertItemPS.setString(4, itemData[3]);  // cedula del cliente
            insertItemPS.setString(5, itemData[2]);  // checkin_state
            insertItemPS.setInt(6, 0);                  // checkout_state (inicialmente 0)
            insertItemPS.setString(7, workerId);        // checkin_by
            insertItemPS.setInt(8, 0);                  // checkout_by (inicialmente 0)
            insertItemPS.setString(9, user.getBusiness_id());  // business_id
            insertItemPS.setString(10, formattedDate);   // checkin_hour
            insertItemPS.setString(11, null);           // checkout_hour (inicialmente null)
            int insertStatus = insertItemPS.executeUpdate();

            if (insertStatus == 1) {
                inserted = true;
            }

            link.commit();
            insertItemPS.close();
            this.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inserted;
    }

    public boolean checkOutItem(String itemId, String businessId, String checkoutState, String checkoutBy, String checkoutHour, Double parkingPrice) throws SQLException {
        //Se conecta a base de datos
        this.connect();
        Connection link = getConnection();
        boolean checkout = false;

        try {
            // Se prepara la sentencia, se asignan valores y se ejecuta para dar salida
            String checkoutItemQuery = "UPDATE item SET checkout_state = ?, checkout_by = ?, checkout_hour = ? WHERE id = ?";
            PreparedStatement checkoutItemPS = link.prepareStatement(checkoutItemQuery);
            checkoutItemPS.setString(1, checkoutState);    // Estado de salida (checkout_state)
            checkoutItemPS.setString(2, checkoutBy);       // Usuario que realiza el checkout (checkout_by)
            checkoutItemPS.setString(3, checkoutHour);     // Hora de salida (checkout_hour)
            checkoutItemPS.setString(4, itemId);           // ID del ítem
            int checkoutExecuted = checkoutItemPS.executeUpdate();

            // Se prepara la sentencia, se asignan valores y se ejecuta para establecer el pago
            String checkoutPaymentQuery = "INSERT INTO `income`(`business_id`, `item_id`, `rate_amount`) VALUES (?, ?, ?)";
            PreparedStatement checkoutPaymentPS = link.prepareStatement(checkoutPaymentQuery);
            checkoutPaymentPS.setString(1, businessId);
            checkoutPaymentPS.setString(2, itemId);     // ID del ítem (antes vehicle_id)
            checkoutPaymentPS.setDouble(3, parkingPrice); // Monto del pago (pay_amount)
            int checkoutPaymentExecuted = checkoutPaymentPS.executeUpdate();

            //Valida si se hizo la actualizacion
            if (checkoutExecuted == 1 && checkoutPaymentExecuted == 1) {
                checkout = true;
                link.commit();
            } else {
                link.rollback();
            }

            checkoutItemPS.close();
            checkoutPaymentPS.close();
        } catch (SQLException ex) {
            link.rollback();
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }

        return checkout;
    }

    public String[][] queryLogs(Boolean[] searchFilters, String[] fields) {
        this.connect();
        Connection link = getConnection();

        // Actualizar la consulta SQL para la nueva base de datos
        String searchLogsQuery = "SELECT i1.*, "
                + "c1.color_name, "
                + "s1.state_name AS checkin_state_name, "
                + "s2.state_name AS checkout_state_name, "
                + "u1.name AS checkin_by_user_name, "
                + "u2.name AS checkout_by_user_name, "
                + "i1.business_id, "
                + "inc1.rate_amount, "
                + "it.type_name " // Se asume que item_type_name es el campo que tiene el nombre del tipo de ítem
                + "FROM item i1 "
                + "LEFT JOIN color c1 ON i1.color = c1.id "
                + "LEFT JOIN state s1 ON i1.checkin_state = s1.id "
                + "LEFT JOIN state s2 ON i1.checkout_state = s2.id "
                + "LEFT JOIN my_user u1 ON i1.checkin_by = u1.id "
                + "LEFT JOIN my_user u2 ON i1.checkout_by = u2.id "
                + "LEFT JOIN income inc1 ON i1.id = inc1.item_id "
                + "LEFT JOIN type it ON i1.item_type = it.id ", whereQuery = "",
                orderByQuery = " ORDER BY i1.id ASC";

        // Se pregunta qué condiciones de búsqueda se van a aplicar a la consulta
        String caseKey = (searchFilters[0] ? "1" : "0")
                + (searchFilters[1] ? "1" : "0")
                + (searchFilters[2] ? "1" : "0");

        // Se evalúa la cadena de caracteres y se guarda la consulta del case que sea verdadera 
        switch (caseKey) {
            case "111":
                whereQuery = " WHERE i1.item_identifiquer = " + fields[0] + " AND i1.checkin_state = " + fields[1] + " AND i1.checkin_by = " + fields[2];
                searchLogsQuery += whereQuery;
                break;
            case "110":
                whereQuery = " WHERE i1.item_identifiquer = " + fields[0] + " AND i1.checkin_state = " + fields[1];
                searchLogsQuery += whereQuery;
                break;
            case "011":
                whereQuery = " WHERE i1.checkin_state = " + fields[1] + " AND i1.checkin_by = " + fields[2];
                searchLogsQuery += whereQuery;
                break;
            case "101":
                whereQuery = " WHERE i1.item_identifiquer = " + fields[0] + " AND i1.checkin_by = " + fields[2];
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
                whereQuery = " WHERE i1.checkin_by = " + fields[2];
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
                    logsData[range][1] = searchLogsRS.getString("type_name"); // Se agrega el valor de item_type
                    logsData[range][2] = searchLogsRS.getString("color_name");
                    logsData[range][3] = searchLogsRS.getString("checkin_state_name");
                    logsData[range][4] = searchLogsRS.getString("checkout_state_name");
                    logsData[range][5] = searchLogsRS.getString("checkin_by_user_name");
                    logsData[range][6] = searchLogsRS.getString("checkout_by_user_name");
                    logsData[range][7] = searchLogsRS.getString("client");
                    logsData[range][8] = searchLogsRS.getString("item_identifiquer");
                    logsData[range][9] = searchLogsRS.getString("checkin_hour");
                    logsData[range][10] = searchLogsRS.getString("checkout_hour");
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

    public int[] countLogs(String dateToSearch) {
        this.connect();
        Connection link = getConnection();

        int[] totalItemsCount = new int[3];

        String dayStart = TimeMethods.formatDate() + " 00:00:00";
        String dayEnd = TimeMethods.formatDate() + " 23:59:59";

        String query = "SELECT ct.business_type FROM item i "
                + "INNER JOIN category ct ON i.business_id = ct.id "
                + "WHERE i.checkin_hour BETWEEN ? AND ?";

        try {
            PreparedStatement countItemsPS = link.prepareStatement(query);
            countItemsPS.setString(1, dayStart);
            countItemsPS.setString(2, dayEnd);
            ResultSet countItemsRS = countItemsPS.executeQuery();

            while (countItemsRS.next()) {
                String businessType = countItemsRS.getString("business_type");

                if (businessType.equals("carro")) {
                    totalItemsCount[0]++;
                } else if (businessType.equals("moto")) {
                    totalItemsCount[1]++;
                } else if (businessType.equals("bicicleta")) {
                    totalItemsCount[2]++;
                }
            }

            return totalItemsCount;

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String[][] getCheckoutData(String dateToSearch) {
        this.connect();
        Connection link = getConnection();

        // Actualizar la consulta SQL para la nueva base de datos
        String innerJoinQuery = " INNER JOIN type t1 ON i1.item_type = t1.id "
                + "INNER JOIN my_user mu ON i1.checkout_by = mu.id "
                + "INNER JOIN income inc1 ON i1.id = inc1.item_id AND i1.business_id = inc1.business_id";

        String whereQuery = " WHERE i1.checkout_hour BETWEEN '" + dateToSearch + " 00:00:00' AND '" + dateToSearch + " 23:59:59'";

        String checkoutCountQuery = "SELECT i1.id, t1.type_name AS type_name, mu.name, mu.last_name, inc1.rate_amount "
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
                    checkoutData[range][2] = checkoutCountRS.getString("rate_amount");
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

                if (link != null) {
                    link.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return checkoutData;
    }

    public ArrayList<Object> getColorsName() {
        this.connect();
        Connection link = getConnection();

        ArrayList<Object> colorData = new ArrayList<>();
        ArrayList<String> colorID = new ArrayList<>();
        ArrayList<String> color = new ArrayList<>();

        try {
            String colorQuery = "SELECT * FROM color";
            PreparedStatement colorPS = link.prepareStatement(colorQuery);
            ResultSet colorRS = colorPS.executeQuery(colorQuery);

            while (colorRS.next()) {
                colorID.add(colorRS.getString("id"));
                color.add(colorRS.getString("color_name"));
            }

            colorData.add(colorID);
            colorData.add(color);

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return colorData;
    }
    
    public ArrayList<Object> getRatesName() {
        this.connect();
        Connection link = getConnection();

        ArrayList<Object> rateData = new ArrayList<>();
        ArrayList<String> rateID = new ArrayList<>();
        ArrayList<String> rate = new ArrayList<>();

        try {
            String rateQuery = "SELECT id, type_name FROM type";
            PreparedStatement colorPS = link.prepareStatement(rateQuery);
            ResultSet colorRS = colorPS.executeQuery();

            while (colorRS.next()) {
                rateID.add(colorRS.getString("id"));
                rate.add(colorRS.getString("type_name"));
            }

            rateData.add(rateID);
            rateData.add(rate);

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rateData;
    }

    public ArrayList<Object> getStatesName() {
        this.connect();
        Connection link = getConnection();

        ArrayList<String> stateID = new ArrayList<>();
        ArrayList<String> state = new ArrayList<>();
        ArrayList<Object> stateData = new ArrayList<>();

        try {
            String stateQuery = "SELECT * FROM state";
            PreparedStatement statePS = link.prepareStatement(stateQuery);
            ResultSet stateRS = statePS.executeQuery(stateQuery);

            while (stateRS.next()) {
                stateID.add(stateRS.getString("id"));
                state.add(stateRS.getString("state_name"));
            }

            stateData.add(stateID);
            stateData.add(state);

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stateData;
    }

}
