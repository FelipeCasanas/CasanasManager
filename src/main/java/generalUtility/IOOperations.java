/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generalUtility;

import java.awt.Component;
import javax.swing.JOptionPane;
import corePackage.User;

/**
 *
 * @author Felipe
 */
public class IOOperations {

    User user = new User();
    
    
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
        switch (type) {
            case "MOTO":
                type = "1";
                break;
            case "CARRO":
                type = "2";
                break;
            case "BICICLETA":
                type = "3";
                break;
        }

        return type;
    }

    public static String parseVehicleColorToCode(String color) {
        switch (color) {
            case "BLANCO":
                color = "1";
                break;
            case "GRIS":
                color = "2";
                break;
            case "NEGRO":
                color = "3";
                break;
            case "ROJO":
                color = "4";
                break;
            case "AZUL":
                color = "5";
                break;
            default:
                color = "6";
        }

        return color;
    }

    public static String parseVehicleStateToCode(String state) {
        switch (state) {
            case "OK":
                state = "1";
                break;
            case "RAYON(ES)":
                state = "2";
                break;
            case "GOLPE(S)":
                state = "3";
                break;
            case "DESCONOCIDO":
                state = "4";
                break;
        }

        return state;
    }

    //Comparan el valor del combo box y retorna el texto equivalente al vehiculo
    public static String parseVehicleTypeToLetters(String type) {
        switch (type) {
            case "1":
                type = "MOTO";
                break;
            case "2":
                type = "CARRO";
                break;
            case "3":
                type = "BICICLETA";
                break;
        }

        return type;
    }

    public static String parseVehicleColorToLetters(String color) {
        switch (color) {
            case "1":
                color = "BLANCO";
                break;
            case "2":
                color = "GRIS";
                break;
            case "3":
                color = "NEGRO";
                break;
            case "4":
                color = "ROJO";
                break;
            case "5":
                color = "AZUL";
                break;
            default:
                color = "OTRO";
        }

        return color;
    }

    public static String parseVehicleStateToLetters(String state) {
        switch (state) {
            case "1":
                state = "OK";
                break;
            case "2":
                state = "RAYON(ES)";
                break;
            case "3":
                state = "GOLPE(S)";
                break;
            case "4":
                state = "DESCONOCIDO";
                break;
        }

        return state;
    }

}
