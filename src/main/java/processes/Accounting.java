/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processes;

/**
 *
 * @author Felipe
 */
public class Accounting {
    
    private static double balance, income, outcome;
    private static double carsIncome, motorcyclesIncome, bikesIncome;

    protected void balance() {
        balance = income-outcome;
    }
    
    private void getVehiclesData() {
        VehiclesManagment vehiclesManagment = new VehiclesManagment();
        //Bucle
            //Incremento carsIncome en el valor de cada registro
            //Incremento motorcyclesIncome en el valor de cada registro
            //Incremento bikesIncome en el valor de cada registro
        //Sumo los tipos de vehiculos para obtener el total
    }
    
    private void calculateTotalIncome() {
        income = carsIncome+motorcyclesIncome+bikesIncome;
    }
    
    public static double getCarsIncome() {
        return carsIncome;
    }

    public static double getMotorcyclesIncome() {
        return motorcyclesIncome;
    }
    
    public static double getBikesIncome() {
        return bikesIncome;
    }
    
    private static void setCarsIncome(double carsIncome) {
        Accounting.carsIncome = carsIncome;
    }

    private static void setMotorcyclesIncome(double motorcyclesIncome) {
        Accounting.motorcyclesIncome = motorcyclesIncome;
    }

    private static void setBikesIncome(double bikesIncome) {
        Accounting.bikesIncome = bikesIncome;
    }
    
}
