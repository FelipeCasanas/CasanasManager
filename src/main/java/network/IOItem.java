/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import controller.User;
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
public class IOItem {

    public boolean insertItem(String[] itemData, String workerId, String formattedDate, Double buyPrice) {
        String itemQuery = """
    INSERT INTO item (
        item_identifiquer, item_type, color, client, checkin_state, checkout_state, 
        checkin_by, checkout_by, business_id, checkin_hour, checkout_hour
    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
    """;

        String incomeQuery = """
    INSERT INTO income (business_id, item_id, buy_price, sell_price) 
    VALUES (?, ?, ?, ?);
    """;

        try (Connection link = Connect.getInstance().getConnection()) {
            // Verificar si la conexión está cerrada
            if (link == null || link.isClosed()) {
                throw new SQLException("Conexión cerrada o nula");
            }

            link.setAutoCommit(false);  // Desactivar auto-commit para manejar la transacción manualmente

            try (
                    PreparedStatement itemPS = link.prepareStatement(itemQuery, PreparedStatement.RETURN_GENERATED_KEYS); PreparedStatement incomePS = link.prepareStatement(incomeQuery)) {
                User user = new User(); // Obtener información del usuario

                // Configurar parámetros del `PreparedStatement` para la tabla `item`
                itemPS.setString(1, itemData[4]);  // item_identifiquer
                itemPS.setInt(2, Integer.parseInt(itemData[0]));  // item_type
                itemPS.setString(3, itemData[1]);  // color
                itemPS.setString(4, itemData[3]);  // client
                itemPS.setString(5, itemData[2]);  // checkin_state
                itemPS.setInt(6, 0);               // checkout_state (inicialmente 0)
                itemPS.setString(7, workerId);     // checkin_by
                itemPS.setInt(8, 0);               // checkout_by (inicialmente 0)
                itemPS.setString(9, user.getBusiness_id());  // business_id
                itemPS.setString(10, formattedDate);  // checkin_hour
                itemPS.setNull(11, java.sql.Types.TIMESTAMP); // checkout_hour (nulo)

                // Ejecutar la inserción en `item`
                int rowsAffected = itemPS.executeUpdate();

                if (rowsAffected == 1) {
                    // Obtener el ID del item recién insertado
                    try (ResultSet generatedKeys = itemPS.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int itemId = generatedKeys.getInt(1);

                            // Configurar parámetros del `PreparedStatement` para la tabla `income`
                            incomePS.setString(1, user.getBusiness_id());
                            incomePS.setInt(2, itemId);
                            incomePS.setDouble(3, buyPrice);
                            incomePS.setDouble(4, 0);

                            // Ejecutar la inserción en `income`
                            int incomeRowsAffected = incomePS.executeUpdate();

                            if (incomeRowsAffected == 1) {
                                link.commit();  // Confirmar ambas inserciones
                                return true;
                            } else {
                                link.rollback();  // Revertir si `income` falla
                            }
                        } else {
                            link.rollback();  // Revertir si no se obtiene el ID del item
                        }
                    }
                } else {
                    link.rollback();  // Revertir si `item` falla
                }

            } catch (SQLException ex) {
                link.rollback();  // Revertir en caso de error
                Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error durante la transacción", ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error en conexión con DB", ex);
        }

        return false;  // Retorna false si ocurrió algún error
    }

    public boolean checkOutItem(String itemId, String businessId, String checkoutState,
            String checkoutBy, String checkoutHour, Double sellPrice) {

        String updateItemQuery = """
    UPDATE item 
    SET checkout_state = ?, checkout_by = ?, checkout_hour = ? 
    WHERE id = ?;
    """;

        String insertIncomeQuery = """
    UPDATE income SET sell_price = ? WHERE business_id = ? AND item_id = ?; """;

        // Usamos try-with-resources para la conexión y los prepared statements
        try (Connection link = Connect.getInstance().getConnection()) {

            // Verificar si la conexión está cerrada
            if (link == null || link.isClosed()) {
                throw new SQLException("Conexion cerrada o nula");
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
                insertIncomePS.setDouble(1, sellPrice);
                insertIncomePS.setString(2, businessId);
                insertIncomePS.setString(3, itemId);
                

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
                Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error durante checkout", ex);
            }

        } catch (SQLException ex) {
            // Manejo de error en la conexión a la base de datos
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error en conexion con DB", ex);
        }

        return false;  // Retornar false si ocurrió algún error
    }
}
