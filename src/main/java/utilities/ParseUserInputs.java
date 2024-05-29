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
            case "Carro":
                type = "1";
                break;
            case "Moto":
                type = "2";
                break;
            case "Bicicleta":
                type = "3";
                break;
        }

        return type;
    }

    public String parseVehicleColorToCode(String color) {
        switch (color) {
            case "Blanco":
                color = "white";
                break;
            case "Gris":
                color = "gray";
                break;
            case "Negro":
                color = "black";
                break;
            case "Azul":
                color = "blue";
                break;
            case "Rojo":
                color = "red";
                break;
        }

        return color;
    }

    public String parseVehicleStateToCode(String state) {
        switch (state) {
            case "Bueno":
                state = "1";
                break;
            case "Rayon(es)":
                state = "2";
                break;
            case "Golpe(s)":
                state = "3";
                break;
            case "Desconocido":
                state = "4";
                break;
        }

        return state;
    }

    //Comparan el valor del combo box y retorna el texto equivalente al vehiculo
    public String parseVehicleTypeToLetters(String type) {
        return null;
    }

    public String parseVehicleColorToLetters(String color) {
        return null;
    }

    public String parseVehicleStateToLetters(String state) {
        return null;
    }

}
