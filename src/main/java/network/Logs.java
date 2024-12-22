/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import utilities.TimeMethods;
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
public class Logs {

    public String[][] queryLogs(Boolean[] searchFilters, String[] fields) {
        // Base de la consulta SQL
        String baseQuery = """
    SELECT i1.*, c1.color_name, s1.state_name AS checkin_state_name, 
    s2.state_name AS checkout_state_name, u1.name AS checkin_by_user_name, 
    u2.name AS checkout_by_user_name, i1.business_id, inc1.rate_amount, it.type_name 
    FROM item i1 
    LEFT JOIN color c1 ON i1.color = c1.id 
    LEFT JOIN state s1 ON i1.checkin_state = s1.id 
    LEFT JOIN state s2 ON i1.checkout_state = s2.id 
    LEFT JOIN my_user u1 ON i1.checkin_by = u1.id 
    LEFT JOIN my_user u2 ON i1.checkout_by = u2.id 
    LEFT JOIN income inc1 ON i1.id = inc1.item_id 
    LEFT JOIN type it ON i1.item_type = it.id
    """;

        // Inicializar cláusula WHERE y ORDER BY
        StringBuilder whereClause = new StringBuilder(" WHERE 1=1 ");
        String orderByClause = " ORDER BY i1.id ASC";

        // Construir la cláusula WHERE dinámica basada en los filtros de búsqueda
        if (searchFilters[0] && !searchFilters[1] && !searchFilters[2]) {
            whereClause = new StringBuilder(" WHERE i1.id = ? ");
        } else {
            if (searchFilters[0]) {
                whereClause.append(" AND i1.item_type = ? ");
            }
            if (searchFilters[1]) {
                whereClause.append(" AND i1.checkin_state = ? ");
            }
            if (searchFilters[2]) {
                whereClause.append(" AND i1.checkin_by = ? ");
            }
        }

        String finalQuery = baseQuery + whereClause + orderByClause;

        // Manejo de conexión a la base de datos y ejecución de la consulta
        try (Connection link = Connect.getInstance().getConnection()) {

            // Verificar si la conexión está cerrada
            if (link == null || link.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }

            try (PreparedStatement ps = link.prepareStatement(finalQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

                // Configurar los parámetros del PreparedStatement
                int paramIndex = 1;
                if (searchFilters[0]) {
                    ps.setString(paramIndex++, fields[0]);
                }
                if (searchFilters[1]) {
                    ps.setString(paramIndex++, fields[1]);
                }
                if (searchFilters[2]) {
                    ps.setString(paramIndex++, fields[2]);
                }

                // Ejecutar la consulta y procesar el resultado
                try (ResultSet rs = ps.executeQuery()) {
                    // Contar el total de filas
                    rs.last();
                    int totalRows = rs.getRow();
                    if (totalRows == 0) {
                        return null;
                    }

                    // Crear una matriz para almacenar los resultados
                    String[][] logsData = new String[totalRows][12];
                    rs.beforeFirst();

                    int rowIndex = 0;
                    while (rs.next()) {
                        logsData[rowIndex][0] = rs.getString("id");
                        logsData[rowIndex][1] = rs.getString("type_name").toUpperCase();
                        logsData[rowIndex][2] = rs.getString("color_name").toUpperCase();
                        logsData[rowIndex][3] = rs.getString("checkin_state_name").toUpperCase();
                        logsData[rowIndex][4] = rs.getString("checkout_state_name").toUpperCase();
                        logsData[rowIndex][5] = rs.getString("checkin_by_user_name").toUpperCase();
                        logsData[rowIndex][6] = rs.getString("checkout_by_user_name").toUpperCase();
                        logsData[rowIndex][7] = rs.getString("client");
                        logsData[rowIndex][8] = rs.getString("item_identifiquer").toUpperCase();
                        logsData[rowIndex][9] = rs.getString("checkin_hour");
                        logsData[rowIndex][10] = rs.getString("checkout_hour");
                        logsData[rowIndex][11] = rs.getString("rate_amount");
                        rowIndex++;
                    }
                    return logsData;
                }

            } catch (SQLException ex) {
                Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error executing query", ex);
            }

        } catch (SQLException ex) {
            // Manejo de error en la conexión a la base de datos
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }

        return null;  // Retornar null si hubo un error o no se encontraron resultados
    }

    public int[] countLogs(String dateToSearch) {
        // Formatear las fechas de inicio y fin del día para la consulta
        String dayStart = TimeMethods.formatDate() + " 00:00:00";
        String dayEnd = TimeMethods.formatDate() + " 23:59:59";

        // Consulta SQL para contar los logs según la hora de check-in
        String query = """
    SELECT ct.business_type 
    FROM item i
    INNER JOIN category ct ON i.business_id = ct.id
    WHERE i.checkin_hour BETWEEN ? AND ? 
    """;

        // Array para almacenar el total de elementos por tipo
        int[] totalItemsCount = new int[3]; // [carro, moto, bicicleta]

        // Manejo de la conexión y ejecución de la consulta
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement countItemsPS = link.prepareStatement(query)) {

            // Verificar que la conexión es válida antes de continuar
            if (link == null || link.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }

            // Configurar los parámetros de la consulta
            countItemsPS.setString(1, dayStart);
            countItemsPS.setString(2, dayEnd);

            // Ejecutar la consulta y contar los tipos de negocios
            try (ResultSet countItemsRS = countItemsPS.executeQuery()) {
                while (countItemsRS.next()) {
                    String businessType = countItemsRS.getString("business_type");
                    switch (businessType) {
                        case "carro":
                            totalItemsCount[0]++;
                            break;
                        case "moto":
                            totalItemsCount[1]++;
                            break;
                        case "bicicleta":
                            totalItemsCount[2]++;
                            break;
                        default:
                            // Si el tipo de negocio es desconocido, no hacer nada
                            break;
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Database error", ex);
            return null;  // Retornar null si ocurre un error en la base de datos
        }

        return totalItemsCount; // Retornar el array con los conteos por tipo
    }
}
