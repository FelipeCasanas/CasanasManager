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
public class UserData {

    // Método para obtener los datos del usuario según permisos
    public String[] get(String email, int permissions) {
        // Se declara la variable de consulta y la de datos de usuario
        String query;
        String[] userData;

        // Determina la consulta y el tamaño del array de datos según los permisos
        if (permissions == 1) {
            query = "SELECT * FROM my_user WHERE email = ?";
            userData = new String[10];  // Para permisos 1, esperamos más datos
        } else if (permissions == 0) {
            query = "SELECT name, last_name, business_id, email, active FROM my_user WHERE id = ?";
            userData = new String[5];  // Para permisos 0, solo ciertos datos
        } else {
            return null;  // Si los permisos no son válidos, retornamos null
        }

        // Obtener la conexión fuera del bloque try-with-resources
        try {
            Connection link = Connect.getInstance().getConnection();  // Usamos el Singleton
            PreparedStatement loginPS = link.prepareStatement(query);
            loginPS.setString(1, email);  // Establece el parámetro de la consulta

            // Ejecuta la consulta y obtiene los resultados
            try (ResultSet queryResult = loginPS.executeQuery()) {
                if (queryResult.next()) {
                    // Si los permisos son 1, asigna todos los campos a userData
                    if (permissions == 1) {
                        userData[0] = queryResult.getString("id");
                        userData[1] = queryResult.getString("card_id");
                        userData[2] = queryResult.getString("name");
                        userData[3] = queryResult.getString("last_name");
                        userData[4] = queryResult.getString("birth_day");
                        userData[5] = queryResult.getString("business_id");
                        userData[6] = queryResult.getString("email");
                        userData[7] = queryResult.getString("password");
                        userData[8] = queryResult.getString("active");
                        userData[9] = queryResult.getString("admin");
                    } else {  // Si los permisos son 0, asigna los campos relevantes
                        userData[0] = queryResult.getString("name");
                        userData[1] = queryResult.getString("last_name");
                        userData[2] = queryResult.getString("business_id");
                        userData[3] = queryResult.getString("email");
                        userData[4] = queryResult.getString("active");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userData;  // Retorna los datos del usuario
    }
}
