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
public class Authentication {

    // Método de autenticación
    public boolean auth(String email, String password) {
        String query = "SELECT id FROM my_user WHERE email = ? AND password = ? AND active = ?;";

        // Obtener la conexión usando el Singleton Connect
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement loginPS = link.prepareStatement(query)) {

            loginPS.setString(1, email);
            loginPS.setString(2, password);
            loginPS.setString(3, "1");  // Aseguramos que el usuario esté activo

            // Ejecutar la consulta y verificar si el usuario existe
            try (ResultSet loginResult = loginPS.executeQuery()) {
                return loginResult.next();  // Si hay resultados, el usuario es válido
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);  // Manejo de errores
            return false;  // Si ocurre un error, retornamos false
        }
    }
}
