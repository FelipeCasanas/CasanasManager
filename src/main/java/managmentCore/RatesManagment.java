/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managmentCore;

import connection.QueryManagment;
import graphics.Rates;
import java.awt.Component;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe
 */
public class RatesManagment {

    private static double carRate, motorcycleRate, bikeRate;

    //Consulta tarifas a base de datos y las establece en parametros de la clase
    public void askRatesToDB() {
        QueryManagment queryManagment = new QueryManagment();
        String[] returnedRates = queryManagment.queryRates();

        carRate = Double.parseDouble(returnedRates[0]);
        motorcycleRate = Double.parseDouble(returnedRates[1]);
        bikeRate = Double.parseDouble(returnedRates[2]);
    }

    //TEMPORALMENTE NO SE USA YA QUE PREGUNTA A DB PARA ACUTALIZAR AL CAMBIAR TARIFAS
    private void setRates(String[] rates) {
        carRate = Double.parseDouble(rates[0]);
        motorcycleRate = Double.parseDouble(rates[1]);
        bikeRate = Double.parseDouble(rates[2]);
    }

    public void changeRate(Component component, String type, double newRate) throws SQLException {
        QueryManagment queryManagment = new QueryManagment();
        boolean updated = false;

        //Manda a QueryManagment a hacer consulta de update para el campo correspondiente
        switch (type) {
            case "car":
                updated = queryManagment.updateRates(newRate, 1);
                break;
            case "motorcycle":
                updated = queryManagment.updateRates(newRate, 2);
                break;
            case "bike":
                updated = queryManagment.updateRates(newRate, 3);
                break;
        }

        if (updated == true) {
            //Actualiza el valor de la tarifa en los atributos de la clase
            updateLocalRate(type, newRate);
        } else {
            JOptionPane.showMessageDialog(component, "No se pudo actualizar la tarifa");
        }
    }

    //Recibe el tipo de vehiculo y el valor de la nueva tarifa y actualiza los atributos de la clase
    private void updateLocalRate(String type, double rate) {
        switch (type) {
            case "car":
                setCarRate(rate);
                break;
            case "motorcycle":
                setMotorcycleRate(rate);
                break;
            case "bike":
                setBikeRate(rate);
                break;
        }
    }

    public double getCarRate() {
        return carRate;
    }

    public double getMotorcycleRate() {
        return motorcycleRate;
    }

    public double getBikeRate() {
        return bikeRate;
    }

    //METODOS USADOS PARA ESTABLECER LA INFORMACION OBTENIDA DE LA BASE DE DATOS
    private void setCarRate(double carRate) {
        this.carRate = carRate;
    }

    private void setMotorcycleRate(double motorcycleRate) {
        this.motorcycleRate = motorcycleRate;
    }

    private void setBikeRate(double bikeRate) {
        this.bikeRate = bikeRate;
    }
}
