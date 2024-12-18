/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstractModel;

import java.awt.Component;

/**
 *
 * @author Felipe
 */
public abstract class ManageBussiness {

    private static int[] storedElementsQuantity; //Arreglo que guarda cada total de elementos en las categorias requeridas
    private static double[] cashIncome; //Arreglo que guarda cada ingreso en las categorias requeridas

    //newElementArguments: Datos del vehiculo o producto
    //auxiliarData: (0)Tabla=vehicle (1)ID usuario (2)Fecha
    public abstract void checkIn(Component view, String[] elementArguments, String[] auxuliarData);

    //leaveAwayElementArguments = vehicleId, checkoutState, parkingPrice
    //auxiliarData = checkoutBy, checkoutHour
    public abstract boolean checkOut(Component view, String[] elementArguments, String[] auxuliarData);
}
