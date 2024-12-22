/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Felipe
 */
public class Connect {

    private static Connect instance;
    private Connection link;

    // Constructor privado para evitar instanciación externa
    private Connect() {
        link = null;
        connect();
    }

    // Método estático para obtener la instancia única
    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    // Método para realizar la conexión
    private void connect() {
        String dbUrl = "jdbc:mysql://localhost:3306/parking_managment";
        String dbUser = "root";
        String dbPassword = "";

        try {
            link = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            link.setAutoCommit(false);  // Deshabilitar autocommit si usas transacciones

            if (link != null) {
                System.out.println("Connection successful");
            }
        } catch (SQLException ex) {
            System.out.println("Error while connecting: " + ex);
        }
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        try {
            if (link == null || link.isClosed()) {
                connect();  // Si la conexión está cerrada, vuelve a abrirla
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking connection: " + ex);
        }
        return link;
    }

    // Método para cerrar la conexión
    public void closeConnection() throws SQLException {
        if (link != null && !link.isClosed()) {
            link.commit();  // Asegúrate de hacer commit si usas transacciones
            link.close();
            link = null;
            System.out.println("The connection has been closed");
        }
    }
}
