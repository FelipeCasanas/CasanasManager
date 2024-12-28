/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import network.Rates;
import network.Checkout;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utilities.TimeMethods;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Business;
import network.IOItem;
import network.Item;

/**
 *
 * @author Felipe
 */
public class BusinessModel {

    public void checkIn(Component view, String[] elementArguments, String[] auxuliarData) {
        try {
            // Verifica si el vehículo ya está en el parqueadero
            Item item = new Item();
            IOItem iOItem = new IOItem();
            double buyPrice = 0;
            boolean vehicleExists = item.itemStillHere(elementArguments[4]);

            //Si tipo de negocio diferente a parqueadero pide que ingrese lo que costo comprar el producto
            if (Business.isRetail()) {
                buyPrice = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el costo del producto: "));
            }

            // Si el vehículo no está en el parqueadero, procede con el check-in
            if (!vehicleExists && JOptionPane.showConfirmDialog(view, "¿Está seguro que desea hacer el ingreso?") == 0) {
                // Realiza la inserción del vehículo
                boolean vehicleInserted = iOItem.insertItem(
                        elementArguments, new User().getId(), TimeMethods.formatFullDate(), buyPrice
                );

                // Muestra mensaje dependiendo del resultado de la inserción
                JOptionPane.showMessageDialog(view, vehicleInserted ? "Se realizó el ingreso con éxito" : "ERROR, no se pudo ingresar");
            } else if (vehicleExists) {
                JOptionPane.showMessageDialog(view, "Ya se encuentra en el establecimiento");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean checkOut(Component view, String[] elementArguments, String[] auxiliaryData) {
        boolean checkoutCompleted = false;

        try {
            Item item = new Item();
            IOItem iOItem = new IOItem();

            if (!item.itemStillHere(elementArguments[2])) {
                JOptionPane.showMessageDialog(view, "No se encuentra en el establecimiento.");
                return checkoutCompleted; // Termina temprano si el vehículo no está
            }

            // Obtiene la información del vehículo y la tarifa
            String[] vehicleData = item.searchItem(elementArguments[2]);

            double parkingPrice = setCheckoutRate(view, vehicleData[9]);

            // Realiza el checkout
            String userId = String.valueOf(new User().getId());
            boolean checkout = iOItem.checkOutItem(
                    vehicleData[0], User.getBusiness_id(), elementArguments[0], userId, TimeMethods.formatFullDate(), parkingPrice
            );

            // Resultado del proceso de checkout
            JOptionPane.showMessageDialog(view, checkout ? "SALIDA COMPLETADA" : "No se pudo realizar la salida, intente de nuevo.");
            checkoutCompleted = checkout;

        } catch (Exception e) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, "Error durante checkout", e);
            JOptionPane.showMessageDialog(view, "Error al procesar la salida. Por favor, intente de nuevo.");
        }

        return checkoutCompleted;
    }

    // Método para manejar la tarifa de estacionamiento
    private double setCheckoutRate(Component view, String itemType) {
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
            Rates rates = new Rates();
            double rate = rates.getRate(itemType);

            //Si no existe la tarifa para el elemento seleccionado
            if (rate == -1) {
                // Inicializar listas para almacenar nuevas tarifas
                ArrayList<String> newRateName = new ArrayList<>();
                ArrayList<Double> newRate = new ArrayList<>();

                if (itemType == null || itemType.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "El nombre de la tarifa no puede estar vacío.");
                    return -1; // Valor por defecto en caso de error
                }
                newRateName.add(itemType);

                String rateValueStr = JOptionPane.showInputDialog(view, "No existe una tarifa para este producto. Ingrese el valor de la nueva tarifa:");
                try {
                    double rateValue = Double.parseDouble(rateValueStr);
                    newRate.add(rateValue);

                    // Crear nueva tarifa
                    if (Rates.createRate(newRateName, newRate)) {
                        JOptionPane.showMessageDialog(view, "Tarifa creada exitosamente.");
                        return rateValue;
                    } else {
                        JOptionPane.showMessageDialog(view, "No se pudo crear la tarifa. Inténtelo nuevamente.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "El valor ingresado no es válido.");
                }
            } else {
                return rate; // Retornar tarifa automática existente
            }
        } else if (option == 1) { // Manual
            String input = JOptionPane.showInputDialog(view, "Ingrese valor a cobrar:");
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Valor no válido. Inténtelo nuevamente.");
            }
        }

        return 0.0; // Retorna 0 en caso de error o cancelación
    }
}
