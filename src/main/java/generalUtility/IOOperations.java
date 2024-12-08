/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generalUtility;

import corePackage.Rates;
import java.awt.Component;
import javax.swing.JOptionPane;
import corePackage.User;
import java.util.ArrayList;
import java.util.StringJoiner;
import network.QueryManagment;

/**
 *
 * @author Felipe
 */
public class IOOperations {

    public static String replaceDateSlash(String dateToSearch) {
        return dateToSearch.replace("/", "-");
    }

    public static boolean compare(String[] textInput, int operationToDo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static boolean validateNonEmptyFields(Component view, String[] data, String[] fields) {
        StringJoiner missingFields = new StringJoiner(", ");

        for (int i = 0; i < data.length; i++) {
            if (data[i].isEmpty()) {
                missingFields.add(fields[i]);
            }
        }

        if (missingFields.length() == 0) {
            return true; // Todos los campos están llenos
        }

        JOptionPane.showMessageDialog(view, "Campos faltantes:\n" + missingFields);
        return false; // Hay campos vacíos
    }

    public static String[] separate(String[] textInput, int operationToDo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String parseVehicleTypeToCode(String type) {
        QueryManagment queryManagment = new QueryManagment();

        // Pendiente hacer dinámico el ID de negocio
        ArrayList<Object> ratesData = queryManagment.getRatesName(User.getBusiness_id());
        @SuppressWarnings("unchecked")
        ArrayList<String> rates = (ArrayList<String>) ratesData.get(0);
        @SuppressWarnings("unchecked")
        ArrayList<String> rateNames = (ArrayList<String>) ratesData.get(1);

        // Busca el índice del tipo en los nombres de las tarifas
        int index = rateNames.indexOf(type);
        if (index != -1) {
            return rates.get(index);
        }

        // Si no se encuentra, devuelve el tipo original
        return type;
    }

    public static String parseVehicleColorToCode(String color) {
        QueryManagment queryManagment = new QueryManagment();

        // Obtiene los datos de colores
        ArrayList<Object> colorData = queryManagment.getColorsName();
        @SuppressWarnings("unchecked")
        ArrayList<Integer> colorIDs = (ArrayList<Integer>) colorData.get(0);
        @SuppressWarnings("unchecked")
        ArrayList<String> colorNames = (ArrayList<String>) colorData.get(1);

        // Busca el índice del color y devuelve el ID correspondiente
        int index = colorNames.indexOf(color);
        if (index != -1) {
            return String.valueOf(colorIDs.get(index));
        }

        // Si no se encuentra, devuelve el color original
        return color;
    }

    public static String parseVehicleStateToCode(String state) {
        QueryManagment queryManagment = new QueryManagment();

        // Obtiene los datos de estados
        ArrayList<Object> stateData = queryManagment.getStatesName();
        @SuppressWarnings("unchecked")
        ArrayList<Integer> stateIDs = (ArrayList<Integer>) stateData.get(0);
        @SuppressWarnings("unchecked")
        ArrayList<String> stateNames = (ArrayList<String>) stateData.get(1);

        // Busca el índice del estado y devuelve el ID correspondiente
        int index = stateNames.indexOf(state);
        if (index != -1) {
            return String.valueOf(stateIDs.get(index));
        }

        // Si no se encuentra, devuelve el estado original
        return state;
    }

}
