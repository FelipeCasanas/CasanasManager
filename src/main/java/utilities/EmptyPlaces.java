/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe
 */
public class EmptyPlaces {

    public boolean validateEmptyPlaces(String[] vehicleInputData, String[] places, Component component, int limit) {
        String accumulator = "";
        int range = 0;

        do {
            if (vehicleInputData[range].isEmpty()) {
                if (accumulator.isEmpty()) {
                    accumulator += places[range];
                } else {
                    accumulator += ", " + places[range];
                }
            }
            range++;
        } while (range < limit);

        if (accumulator.isEmpty()) {
            return false;
        } else {
            String message = "Campos faltantes:\n" + accumulator;
            JOptionPane.showMessageDialog(component, message);
            return true;
        }
    }
}
