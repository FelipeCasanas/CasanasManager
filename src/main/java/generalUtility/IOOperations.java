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
import network.QueryManagment;

/**
 *
 * @author Felipe
 */
public class IOOperations {

    public static String replaceDateSlash(String dateToSearch) {
        
        int dateLength = dateToSearch.length();
        String returnedDate = "";
        
        for (int i = 0; i < dateLength; i++) {
            if (dateToSearch.charAt(i) == '/') {
                returnedDate += "-";
            } else {
                returnedDate += dateToSearch.charAt(i);
            }
        }
        
        
        return returnedDate;
    }
    

    public static boolean compare(String[] textInput, int operationToDo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static boolean validateNonEmptyFields(Component view, String[] data, String[] fields) {
        StringBuilder accumulator = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            if (data[i].isEmpty()) {
                if (accumulator.length() > 0) {
                    accumulator.append(", ");
                }
                accumulator.append(fields[i]);
            }
        }

        if (accumulator.length() == 0) {
            return true; // Todos los campos están llenos
        } else {
            String message = "Campos faltantes:\n" + accumulator.toString();
            JOptionPane.showMessageDialog(view, message);
            return false; // Hay campos vacíos
        }

    }

    public static String[] separate(String[] textInput, int operationToDo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Comparan el valor del combo box y retorna el numero equivalente al vehiculo
    public static String parseVehicleTypeToCode(String type) {
        QueryManagment queryManagment = new QueryManagment();
        
        //Pendiente hacer dinamico ID de negocio
        ArrayList<Object> ratesData = queryManagment.getRatesName(User.getBusiness_id());
        ArrayList<String> rate = (ArrayList<String>) ratesData.get(0);
        ArrayList<String> rateName = (ArrayList<String>) ratesData.get(1);
        
        if(rateName.contains(type)) {
            int index = rateName.indexOf(type);
            type = rate.get(index);
        }

        return type;
    }

    public static String parseVehicleColorToCode(String color) {
        QueryManagment queryManagment = new QueryManagment();
        
        ArrayList<Object> colorData = queryManagment.getColorsName();
        ArrayList<Integer> colorID = (ArrayList<Integer>) colorData.get(0);
        ArrayList<String> colorName = (ArrayList<String>) colorData.get(1);
        
        if(colorName.contains(color)) {
            color = String.valueOf(colorID.get(colorName.indexOf(color)));
        }

        return color;
    }

    public static String parseVehicleStateToCode(String state) {
        QueryManagment queryManagment = new QueryManagment();
        
        ArrayList<Object> stateData = queryManagment.getStatesName();
        ArrayList<Integer> stateID = (ArrayList<Integer>) stateData.get(0);
        ArrayList<String> stateName = (ArrayList<String>) stateData.get(1);
        
        if(stateName.contains(state)) {
            state = String.valueOf(stateID.get(stateName.indexOf(state)));
        }

        return state;
    }
}
