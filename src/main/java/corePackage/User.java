/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corePackage;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Connect;

/**
 *
 * @author Felipe
 */
public class User implements abstractModel.ManageUsers {

    // Variables de instancia (ya no son estáticas)
    private static String id, card_id, name, lastName, birth_day, business_id, businessCategory, email, password, active, admin;

    // Método de autenticación
    @Override
    public boolean authUser(String email, String password) {
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

    // Método para obtener los datos del usuario según permisos
    @Override
    public String[] getUserData(String email, int permissions) {
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

    public void businessCategory() {
    String businessIDQuery = "SELECT category FROM business WHERE id = ?";
    
    // Obtener la conexión fuera del bloque try-with-resources
    try {
        Connection link = Connect.getInstance().getConnection(); // Usar el Singleton para obtener la conexión
        PreparedStatement ps = link.prepareStatement(businessIDQuery);
        ps.setString(1, business_id);  // Establecer el ID del negocio
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                businessCategory = rs.getString("category");  // Obtener la categoría del negocio
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


    // Establecer los datos del usuario
    @Override
    public void setUserData(String[] userData) {
        setId(userData[0]);
        setCard_id(userData[1]);
        setName(userData[2]);
        setLastName(userData[3]);
        setBirth_day(userData[4]);
        setBusiness_id(userData[5]);
        setEmail(userData[6]);
        setPassword(userData[7]);
        setActive(userData[8]);
        setAdmin(userData[9]);
    }

    @Override
    public boolean modifyUser(String[] arguments, int operationToDo) {
        // Implementar la lógica de modificación aquí (no proporcionada en el código original)
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Métodos getter y setter para las variables de instancia (no estáticas)
    public static String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    public static String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public static String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public static String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String category) {
        this.businessCategory = category;
    }
}
