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
public class IOOperations implements abstractModel.ManageText {

    User user = new User();

    @Override
    public boolean compare(String[] textInput, int operationToDo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean validate(Component view, String[] toValidate, String[] auxiliar, int limit) {
        String accumulator = "";
        int range = 0;
        boolean validated = false;

        do {
            if (toValidate[range].isEmpty()) {
                if (accumulator.isEmpty()) {
                    accumulator += auxiliar[range];
                } else {
                    accumulator += ", " + auxiliar[range];
                }
            }
            range++;
        } while (range < limit);

        if (accumulator.isEmpty()) {
            validated = true;
        } else {
            String message = "Campos faltantes:\n" + accumulator;
            JOptionPane.showMessageDialog(view, message);
            validated = false;
        }

        return validated;
    }

    @Override
    public String[] separate(String[] textInput, int operationToDo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cleanString(int operationToDo) {

        switch (operationToDo = 1) {
            case 1:
                user.setId("0");
                user.setCard_id("");
                user.setName("");
                user.setLastName("");
                user.setBirth_day("");
                user.setBusiness_id("0");
                user.setEmail("");
                user.setPassword("");
                user.setActive("0");
                user.setAdmin("0");
                break;

            case 2:

                break;

            case 3:

                break;
            default:
                throw new AssertionError();
        }

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Comparan el valor del combo box y retorna el numero equivalente al vehiculo
    public String parseVehicleTypeToCode(String type) {
        switch (type) {
            case "CARRO":
                type = "1";
                break;
            case "MOTO":
                type = "2";
                break;
            case "BICICLETA":
                type = "3";
                break;
        }

        return type;
    }

    public String parseVehicleColorToCode(String color) {
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
            case "AZUL":
                color = "4";
                break;
            case "ROJO":
                color = "5";
                break;
        }

        return color;
    }

    public String parseVehicleStateToCode(String state) {
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
    public String parseVehicleTypeToLetters(String type) {
        switch (type) {
            case "1":
                type = "CARRO";
                break;
            case "2":
                type = "MOTO";
                break;
            case "3":
                type = "BICICLETA";
                break;
        }

        return type;
    }

    public String parseVehicleColorToLetters(String color) {
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
                color = "AZUL";
                break;
            case "5":
                color = "ROJO";
                break;
        }

        return color;
    }

    public String parseVehicleStateToLetters(String state) {
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
