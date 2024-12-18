/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corePackage;

import abstractModel.ManageBussiness;
import network.QueryManagment;
import java.awt.Component;
import javax.swing.JOptionPane;
import generalUtility.TimeMethods;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class BusinessModel extends ManageBussiness {

    @Override
    public void checkIn(Component view, String[] elementArguments, String[] auxuliarData) {
        try {
            // Verifica si el vehículo ya está en el parqueadero
            QueryManagment queryManagment = new QueryManagment();
            boolean vehicleExists = queryManagment.itemStillHere(elementArguments[4]);

            // Si el vehículo no está en el parqueadero, procede con el check-in
            if (!vehicleExists && JOptionPane.showConfirmDialog(view, "¿Está seguro que desea hacer el ingreso?") == 0) {
                // Realiza la inserción del vehículo
                boolean vehicleInserted = queryManagment.insertItem(
                        elementArguments, new User().getId(), TimeMethods.formatFullDate()
                );
                // Muestra mensaje dependiendo del resultado de la inserción
                JOptionPane.showMessageDialog(view, vehicleInserted ? "Se realizó el ingreso con éxito" : "ERROR, no se pudo ingresar el vehículo");
            } else if (vehicleExists) {
                JOptionPane.showMessageDialog(view, "El vehículo ya se encuentra en el parqueadero");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean checkOut(Component view, String[] elementArguments, String[] auxiliaryData) {
        boolean checkoutCompleted = false;

        try {
            QueryManagment queryManagment = new QueryManagment();
            if (!queryManagment.itemStillHere(elementArguments[2])) {
                JOptionPane.showMessageDialog(view, "El vehículo no se encuentra en el parqueadero.");
                return checkoutCompleted; // Termina temprano si el vehículo no está
            }

            // Obtiene la información del vehículo y la tarifa
            String[] vehicleData = queryManagment.searchItem(elementArguments[2]);
            double parkingPrice = setCheckoutRate(view, vehicleData[9]);

            // Realiza el checkout
            String userId = String.valueOf(new User().getId());
            boolean checkout = queryManagment.checkOutItem(
                    vehicleData[0], User.getBusiness_id(), elementArguments[0], userId, TimeMethods.formatFullDate(), parkingPrice
            );

            // Resultado del proceso de checkout
            JOptionPane.showMessageDialog(view, checkout ? "SALIDA COMPLETADA" : "No se pudo realizar la salida, intente de nuevo.");
            checkoutCompleted = checkout;

        } catch (Exception e) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, "Error during checkout", e);
            JOptionPane.showMessageDialog(view, "Error al procesar la salida. Por favor, intente de nuevo.");
        }

        return checkoutCompleted;
    }

    // Método para manejar la tarifa de estacionamiento
    private double setCheckoutRate(Component view, String vehicleType) {
        // Preguntar al usuario si quiere asignar la tarifa automática o manual
        int option = JOptionPane.showOptionDialog(
                view,
                "¿Asignar tarifa automáticamente o manualmente?",
                "Seleccionar tarifa",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Automática", "Manual"},
                "Automática"
        );

        if (option == 0) { // Automática
            Rates rate = new Rates();

            String[] rateData = rate.searchRate(vehicleType);
            if (rateData.length > 0) {
                return Double.parseDouble(rateData[0]); // Tarifa automática
            }
            JOptionPane.showMessageDialog(view, "Tarifa no encontrada.");
        } else if (option == 1) { // Manual
            try {
                return Double.parseDouble(JOptionPane.showInputDialog(view, "Ingrese valor a cobrar:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Valor no válido.");
            }
        }
        return 0.0; // Retorna 0 en caso de error o cancelación
    }
}
