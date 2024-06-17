/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Felipe
 */
public class ParseUserInputs {

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
