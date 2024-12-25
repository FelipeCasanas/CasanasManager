/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class Checkout {

    public String[][] getCheckoutData(String dateToSearch) {
        // Validar el formato de la fecha
        if (dateToSearch == null || dateToSearch.trim().isEmpty()) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Invalid dateToSearch parameter");
            return null;
        }

        // Definir la consulta SQL para obtener los datos de checkout
        String innerJoinQuery = """
    INNER JOIN type t1 ON i1.item_type = t1.id
    INNER JOIN my_user mu ON i1.checkout_by = mu.id
    INNER JOIN income inc1 ON i1.id = inc1.item_id AND i1.business_id = inc1.business_id
    """;

        String whereQuery = " WHERE i1.checkout_hour BETWEEN ? AND ?";
        String orderByClause = " ORDER BY i1.id ASC";
        String checkoutCountQuery = "SELECT i1.id, t1.type_name AS type_name, mu.name, mu.last_name, inc1.rate_amount "
                + "FROM item i1 " + innerJoinQuery + whereQuery + orderByClause;

        String[][] checkoutData = null;

        // Manejo de la conexión y ejecución de la consulta
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement checkoutCountPS = link.prepareStatement(checkoutCountQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            // Configurar los parámetros de la consulta
            String startDateTime = dateToSearch + " 00:00:00";
            String endDateTime = dateToSearch + " 23:59:59";

            checkoutCountPS.setString(1, startDateTime);
            checkoutCountPS.setString(2, endDateTime);

            try (ResultSet checkoutCountRS = checkoutCountPS.executeQuery()) {
                // Mover al final para obtener el total de filas
                if (checkoutCountRS.last()) {
                    int totalRows = checkoutCountRS.getRow();
                    if (totalRows > 0) {
                        checkoutData = new String[totalRows][5];
                        checkoutCountRS.beforeFirst(); // Volver al inicio para leer los resultados

                        // Iterar sobre el ResultSet y almacenar los resultados
                        int index = 0;
                        while (checkoutCountRS.next()) {
                            checkoutData[index][0] = checkoutCountRS.getString("id");
                            checkoutData[index][1] = checkoutCountRS.getString("type_name").toUpperCase();
                            checkoutData[index][2] = checkoutCountRS.getString("rate_amount");
                            checkoutData[index][3] = checkoutCountRS.getString("name").toUpperCase();
                            checkoutData[index][4] = checkoutCountRS.getString("last_name").toUpperCase();
                            index++;
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            // Registrar el error en el log con detalle de la excepción
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error fetching checkout data for date: " + dateToSearch, ex);
        }

        return checkoutData; // Retornar los datos obtenidos
    }
}
