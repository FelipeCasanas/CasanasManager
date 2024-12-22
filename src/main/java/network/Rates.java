/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import controller.User;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Connect;
import network.Checkout;

/**
 *
 * @author Felipe
 */
public class Rates {

    public boolean createRate(ArrayList<String> newRateName, ArrayList<Double> newRate) {
        // Obtiene la instancia de la conexión usando el patrón Singleton
        Connection link = Connect.getInstance().getConnection();

        try {
            // Indica el id del negocio que está funcionando en este momento
            int businessID = Integer.parseInt(User.getBusiness_id());

            // Inicia la transacción
            link.setAutoCommit(false);

            // String con consulta para insertar nuevas tarifas
            String insertRate = "INSERT INTO price(business_id, rate_name, rate_amount) VALUES (?, ?, ?);";

            try (PreparedStatement insertRatePS = link.prepareStatement(insertRate)) {
                for (int i = 0; i < newRateName.size(); i++) {
                    insertRatePS.setInt(1, businessID);
                    insertRatePS.setString(2, newRateName.get(i));
                    insertRatePS.setDouble(3, newRate.get(i));

                    // Ejecuta la consulta de inserción
                    int inserted = insertRatePS.executeUpdate();

                    // Si no se insertó, realiza rollback y retorna false
                    if (inserted == 0) {
                        link.rollback();
                        return false;
                    }
                }

                // Si todo ha ido bien, hace commit de la transacción
                link.commit();
                return true;
            } catch (SQLException ex) {
                // Si ocurre un error, hace rollback
                link.rollback();
                Logger.getLogger(Rates.class.getName()).log(Level.SEVERE, "Error al insertar tarifas", ex);
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Rates.class.getName()).log(Level.SEVERE, "Error al conectar a la base de datos", ex);
            return false;
        }
    }

    public ArrayList<Object> getRates(int businessID) {
        ArrayList<Object> ratesData = new ArrayList<>();

        // Consulta SQL para obtener tarifas
        String queryRates = "SELECT id, rate_name, rate_amount FROM price WHERE business_id = ?";

        // Manejo automático de la conexión y recursos con try-with-resources
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement ratesPS = link.prepareStatement(queryRates)) {

            // Configura el parámetro de la consulta
            ratesPS.setInt(1, businessID);

            // Ejecuta la consulta y obtiene los resultados
            try (ResultSet ratesRS = ratesPS.executeQuery()) {

                // Declara listas para almacenar los resultados de las tarifas
                ArrayList<Integer> ratesID = new ArrayList<>();
                ArrayList<String> ratesName = new ArrayList<>();
                ArrayList<Double> rates = new ArrayList<>();

                // Recorre el ResultSet y agrega los datos a las listas
                while (ratesRS.next()) {
                    ratesID.add(ratesRS.getInt("id"));
                    ratesName.add(ratesRS.getString("rate_name"));
                    rates.add(ratesRS.getDouble("rate_amount"));
                }

                // Asigna los datos al objeto ratesData
                ratesData.add(ratesID);
                ratesData.add(ratesName);
                ratesData.add(rates);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error al obtener tarifas", ex);
        }

        // Retorna las tarifas obtenidas o null si hubo un error
        return ratesData.isEmpty() ? null : ratesData;
    }

    public String[] getRate(String elementName) {
        String[] rate = new String[1];

        // Consulta SQL para obtener tarifas
        String queryRates = "SELECT rate_amount FROM price WHERE rate_name = ? LIMIT 1";

        // Manejo automático de la conexión y recursos con try-with-resources
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement ratesPS = link.prepareStatement(queryRates)) {

            // Configura el parámetro de la consulta
            ratesPS.setString(1, elementName);

            // Ejecuta la consulta y obtiene los resultados
            try (ResultSet ratesRS = ratesPS.executeQuery()) {

                // Recorre el ResultSet y agrega los datos a las listas
                while (ratesRS.next()) {
                    rate[0] = ratesRS.getString("rate_amount");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error al obtener tarifas", ex);
        }

        // Retorna las tarifas obtenidas o null si hubo un error
        return rate;
    }

    public boolean updateRate(Component view, int businessId, String elementName, double newRate) {
        // Declara la variable que indica si se logró actualizar la tarifa
        boolean rateUpdated = false;

        // Utiliza try-with-resources para asegurar el cierre de los recursos
        String updateRateQuery = "UPDATE price SET rate_amount = ? WHERE business_id = ? AND rate_name = ?";

        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement updateRatePS = link.prepareStatement(updateRateQuery)) {

            // Establece los parámetros de la consulta
            updateRatePS.setDouble(1, newRate);
            updateRatePS.setInt(2, businessId);
            updateRatePS.setString(3, elementName);  // El nombre del elemento a actualizar

            // Ejecuta la actualización
            int executedUpdate = updateRatePS.executeUpdate();

            // Si la actualización fue exitosa, cambia rateUpdated a true
            if (executedUpdate == 1) {
                rateUpdated = true;
                link.commit(); // Confirmar la transacción
            } else {
                link.rollback(); // Revertir cambios si no se actualizó
            }

        } catch (SQLException ex) {
            Logger.getLogger(Rates.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rateUpdated;
    }
}
