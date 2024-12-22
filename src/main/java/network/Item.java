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
public class Item {

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
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE,
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
                Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error fetching item data", ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }

        return itemData;  // Retorna los datos obtenidos o un array vacío si no se encuentran datos
    }

}
