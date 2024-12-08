/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corePackage;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import network.Connect;
import network.QueryManagment;

/**
 *
 * @author Felipe
 */
public class Rates implements abstractModel.ManageRates {

    //Declara 2 listas, el primero String para darle nombre a la tarifa. El segundo tipo Double para el valor de la tarifa
    private static ArrayList<String> newRateName = new ArrayList<>();
    private static ArrayList<Double> rate = new ArrayList<>();

    @Override
    public void addRate(Component view, int[] searchDiscriminant) {
        // Listas para almacenar temporalmente los valores
        ArrayList<String> newRateName = new ArrayList<>();
        ArrayList<Double> newRate = new ArrayList<>();

        while (true) {
            // Solicita el nombre y valor de la nueva tarifa
            String newElementName = JOptionPane.showInputDialog(view, "Como se llamara el nuevo elemento?");
            String newElementValue = JOptionPane.showInputDialog(view, "Que valor tendra el nuevo elemento?");

            // Valida la entrada
            if (newElementName.isEmpty() || newElementValue.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Alguno de los campos esta vacio");
                continue;
            }

            // Verifica si la tarifa ya existe
            if (newRateName.contains(newElementName)) {
                JOptionPane.showMessageDialog(view, "Ya existe un elemento con ese nombre");
            } else {
                // Añade la tarifa si no existe
                newRateName.add(newElementName);
                newRate.add(Double.parseDouble(newElementValue));
            }

            // Pregunta si continuar añadiendo tarifas
            if (JOptionPane.showConfirmDialog(view, "Quiere continuar añadiendo elementos") != JOptionPane.YES_OPTION) {
                break;
            }
        }

        // Sube las tarifas a la base de datos
        uploadRateToDB(newRateName, newRate);
    }

    @Override
    public void addLocalRate(ArrayList<String> ratesName, ArrayList<Double> rates) {
        this.newRateName = ratesName;
        this.rate = rates;
    }

    @Override
    public boolean uploadRateToDB(ArrayList<String> newRateName, ArrayList<Double> newRate) {
        // Obtiene la instancia de la conexión usando el patrón Singleton
        Connection link = Connect.getInstance().getConnection();

        try {
            // Variable temporal que indica el id del negocio que está funcionando en este momento
            int businessID = 1;

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

    @Override
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
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Error al obtener tarifas", ex);
        }

        // Retorna las tarifas obtenidas o null si hubo un error
        return ratesData.isEmpty() ? null : ratesData;
    }

    @Override
    public String[] searchRate(String elementName) {
        // Busca el índice del nombre del elemento
        int elementIndex = newRateName.indexOf(elementName);

        // Si el elemento no se encuentra, retorna un array vacío
        if (elementIndex == -1) {
            return new String[0];
        }

        // Si el elemento se encuentra, construye el array con la información
        return new String[]{
            String.valueOf(elementIndex), // Código de la tarifa
            newRateName.get(elementIndex), // Nombre de la tarifa
            String.valueOf(rate.get(elementIndex)) // Valor de la tarifa
        };
    }

    @Override
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

    @Override
    public boolean deleteRate(Component view, String elementName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
