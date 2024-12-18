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
import java.util.List;

/**
 *
 * @author Felipe
 */
public class QueryManagment {

    public boolean itemStillHere(String itemIdentiquer) {
        String searchItemQuery = "SELECT 1 FROM item WHERE item_identifiquer = ? AND checkout_hour IS NULL";

        // Obtener la conexión usando el Singleton Connect y ejecutar la consulta
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement searchItemPS = link.prepareStatement(searchItemQuery)) {

            // Establecer el parámetro de la consulta
            searchItemPS.setString(1, itemIdentiquer);

            // Ejecuta la consulta y verifica si el artículo está presente (sin checkout)
            try (ResultSet foundItemRS = searchItemPS.executeQuery()) {
                return foundItemRS.next();  // Retorna true si se encuentra el artículo
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE,
                    "Error checking item presence for item_identifiquer: " + itemIdentiquer, ex);  // Manejo de errores
        }

        return false;  // Retorna false si ocurre un error o no se encuentra el artículo
    }

    public String[] searchItem(String search) {
        // La conexión se obtiene a través del Singleton Connect
        String[] itemData = new String[12];
        String query = """
    SELECT 
        i1.id, c1.color_name, s1.state_name AS checkin_state_name, 
        s2.state_name AS checkout_state_name, u1.name AS checkin_by_user_name, 
        u2.name AS checkout_by_user_name, b1.name AS business_name, 
        i1.item_identifiquer, i1.client, t1.type_name, 
        i1.checkin_hour, i1.checkout_hour 
    FROM item i1 
    LEFT JOIN color c1 ON i1.color = c1.id 
    LEFT JOIN state s1 ON i1.checkin_state = s1.id 
    LEFT JOIN state s2 ON i1.checkout_state = s2.id 
    LEFT JOIN my_user u1 ON i1.checkin_by = u1.id 
    LEFT JOIN my_user u2 ON i1.checkout_by = u2.id 
    LEFT JOIN business b1 ON i1.business_id = b1.id 
    LEFT JOIN type t1 ON i1.item_type = t1.id 
    WHERE i1.item_identifiquer = ? 
    ORDER BY i1.id DESC LIMIT 1;
    """;

        try (Connection link = Connect.getInstance().getConnection()) {  // Usando Singleton
            // Verificar si la conexión está cerrada
            if (link == null || link.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }

            try (PreparedStatement ps = link.prepareStatement(query)) {
                ps.setString(1, search);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        for (int i = 0; i < itemData.length; i++) {
                            itemData[i] = rs.getString(i + 1);  // Asigna los resultados al array
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Error fetching item data", ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }

        return itemData;  // Retorna los datos obtenidos o un array vacío si no se encuentran datos
    }

    public boolean insertItem(String[] itemData, String workerId, String formattedDate) {
        String query = """
    INSERT INTO item (
        item_identifiquer, item_type, color, client, checkin_state, checkout_state, 
        checkin_by, checkout_by, business_id, checkin_hour, checkout_hour
    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
    """;

        try (Connection link = Connect.getInstance().getConnection()) {  // Usando Singleton para la conexión
            // Verificar si la conexión está cerrada
            if (link == null || link.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }

            link.setAutoCommit(false);  // Desactivar auto-commit para controlar manualmente la transacción

            try (PreparedStatement ps = link.prepareStatement(query)) {
                User user = new User(); // Obtener información del usuario si es necesario.

                // Configurar parámetros del `PreparedStatement`...
                ps.setString(1, itemData[4]);  // item_identifiquer
                ps.setInt(2, Integer.parseInt(itemData[0]));  // item_type
                ps.setString(3, itemData[1]);  // color
                ps.setString(4, itemData[3]);  // client
                ps.setString(5, itemData[2]);  // checkin_state
                ps.setInt(6, 0);               // checkout_state (inicialmente 0)
                ps.setString(7, workerId);     // checkin_by
                ps.setInt(8, 0);               // checkout_by (inicialmente 0)
                ps.setString(9, user.getBusiness_id());  // business_id
                ps.setString(10, formattedDate);  // checkin_hour
                ps.setNull(11, java.sql.Types.TIMESTAMP); // checkout_hour (nulo)

                // Ejecutar la inserción
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 1) {
                    link.commit();  // Confirmar cambios si la inserción fue exitosa
                    return true;
                } else {
                    link.rollback();  // Revertir si no se insertó correctamente
                    return false;
                }

            } catch (SQLException ex) {
                link.rollback();  // Revertir en caso de error
                Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Error inserting item", ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }

        return false;  // Retorna false si ocurrió un error en la inserción
    }

    public boolean checkOutItem(String itemId, String businessId, String checkoutState,
            String checkoutBy, String checkoutHour, Double parkingPrice) {

        String updateItemQuery = """
    UPDATE item 
    SET checkout_state = ?, checkout_by = ?, checkout_hour = ? 
    WHERE id = ?;
    """;

        String insertIncomeQuery = """
    INSERT INTO income (business_id, item_id, rate_amount) 
    VALUES (?, ?, ?);
    """;

        // Usamos try-with-resources para la conexión y los prepared statements
        try (Connection link = Connect.getInstance().getConnection()) {

            // Verificar si la conexión está cerrada
            if (link == null || link.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }

            link.setAutoCommit(false);  // Desactivar auto-commit para manejar transacciones manualmente

            try (PreparedStatement updateItemPS = link.prepareStatement(updateItemQuery); PreparedStatement insertIncomePS = link.prepareStatement(insertIncomeQuery)) {

                // Configurar y ejecutar la actualización del item
                updateItemPS.setString(1, checkoutState);
                updateItemPS.setString(2, checkoutBy);
                updateItemPS.setString(3, checkoutHour);
                updateItemPS.setString(4, itemId);

                // Si la actualización no se realizó correctamente, revertir cambios
                if (updateItemPS.executeUpdate() != 1) {
                    link.rollback();  // Revertir transacción en caso de error
                    return false;
                }

                // Configurar y ejecutar el registro del ingreso
                insertIncomePS.setString(1, businessId);
                insertIncomePS.setString(2, itemId);
                insertIncomePS.setDouble(3, parkingPrice);

                // Si no se insertó correctamente, revertir cambios
                if (insertIncomePS.executeUpdate() != 1) {
                    link.rollback();  // Revertir transacción en caso de error
                    return false;
                }

                // Confirmar transacción si todo salió bien
                link.commit();
                return true;

            } catch (SQLException ex) {
                // Revertir en caso de cualquier error durante el procesamiento
                link.rollback();
                Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Error during checkout", ex);
            }

        } catch (SQLException ex) {
            // Manejo de error en la conexión a la base de datos
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }

        return false;  // Retornar false si ocurrió algún error
    }

    public String[][] getCheckoutData(String dateToSearch) {
        // Validar el formato de la fecha
        if (dateToSearch == null || dateToSearch.trim().isEmpty()) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Invalid dateToSearch parameter");
            return null;
        }

        // Definir la consulta SQL para obtener los datos de checkout
        String innerJoinQuery = """
    INNER JOIN type t1 ON i1.item_type = t1.id
    INNER JOIN my_user mu ON i1.checkout_by = mu.id
    INNER JOIN income inc1 ON i1.id = inc1.item_id AND i1.business_id = inc1.business_id
    """;

        String whereQuery = " WHERE i1.checkout_hour BETWEEN ? AND ?";
        String checkoutCountQuery = "SELECT i1.id, t1.type_name AS type_name, mu.name, mu.last_name, inc1.rate_amount "
                + "FROM item i1 " + innerJoinQuery + whereQuery;

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
                            checkoutData[index][1] = checkoutCountRS.getString("type_name");
                            checkoutData[index][2] = checkoutCountRS.getString("rate_amount");
                            checkoutData[index][3] = checkoutCountRS.getString("name");
                            checkoutData[index][4] = checkoutCountRS.getString("last_name");
                            index++;
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            // Registrar el error en el log con detalle de la excepción
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Error fetching checkout data for date: " + dateToSearch, ex);
        }

        return checkoutData; // Retornar los datos obtenidos
    }

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
                Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Error executing query", ex);
            }

        } catch (SQLException ex) {
            // Manejo de error en la conexión a la base de datos
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Database connection error", ex);
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
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Database error", ex);
            return null;  // Retornar null si ocurre un error en la base de datos
        }

        return totalItemsCount; // Retornar el array con los conteos por tipo
    }

    public ArrayList<Object> getColorsName() {
        Connection link = Connect.getInstance().getConnection();

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

    public ArrayList<Object> getRatesName(String business_id) {
        Connection link = Connect.getInstance().getConnection();

        ArrayList<Object> rateData = new ArrayList<>();
        ArrayList<String> rateID = new ArrayList<>();
        ArrayList<String> rate = new ArrayList<>();

        try {
            String rateQuery = "SELECT id, type_name FROM type WHERE category_id = ?";
            PreparedStatement colorPS = link.prepareStatement(rateQuery);
            colorPS.setString(1, User.getBusinessCategory());
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
        Connection link = Connect.getInstance().getConnection();

        ArrayList<String> stateID = new ArrayList<>();
        ArrayList<String> state = new ArrayList<>();
        ArrayList<Object> stateData = new ArrayList<>();

        try {
            String stateQuery = "SELECT * FROM state WHERE category_id = ?";
            PreparedStatement statePS = link.prepareStatement(stateQuery);
            statePS.setString(1, User.getBusinessCategory());
            ResultSet stateRS = statePS.executeQuery();

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

    public List<String[]> getEmployesIDs() {
        List<String[]> employees = new ArrayList<>();
        String query = "SELECT id, name, last_name FROM my_user WHERE business_id = ?";

        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement preparedStatement = link.prepareStatement(query)) {

            // Obtiene el ID del negocio
            int businessId = Integer.parseInt(User.getBusiness_id());
            preparedStatement.setInt(1, businessId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String[] employeeData = new String[3];
                    employeeData[0] = resultSet.getString("id");
                    employeeData[1] = resultSet.getString("name");
                    employeeData[2] = resultSet.getString("last_name");
                    employees.add(employeeData);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los empleados: " + e.getMessage());
            e.printStackTrace();
        }

        return employees;
    }

}
