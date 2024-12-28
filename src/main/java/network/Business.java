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
public class Business {

    public void getCategory() {
        String businessIDQuery = "SELECT category FROM business WHERE id = ?";

        // Obtener la conexión fuera del bloque try-with-resources
        try {
            Connection link = Connect.getInstance().getConnection(); // Usar el Singleton para obtener la conexión
            PreparedStatement ps = link.prepareStatement(businessIDQuery);
            ps.setString(1, User.getBusiness_id());  // Establecer el ID del negocio

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    new User().setBusinessCategory(rs.getString("category"));
                }
            }

            // Opcional: Si deseas cerrar la conexión manualmente al final, hacerlo aquí
            if (link != null && !link.isClosed()) {
                link.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);  // Manejo de errores
        }
    }

    public static boolean isRetail() {
        String businessIDQuery = "SELECT retail FROM category WHERE id = ?";

        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement ps = link.prepareStatement(businessIDQuery)) {

            ps.setString(1, User.getBusinessCategory()); // Establecer el ID del negocio

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("retail"); // Devuelve el valor del campo "retail"
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error querying retail status", ex);
        }

        return false; // Retorna false si no hay resultados o ocurre un error
    }

}
