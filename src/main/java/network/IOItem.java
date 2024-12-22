/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import controller.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class IOItem {

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
                Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error inserting item", ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Database connection error", ex);
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
                Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error during checkout", ex);
            }

        } catch (SQLException ex) {
            // Manejo de error en la conexión a la base de datos
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }

        return false;  // Retornar false si ocurrió algún error
    }
}
