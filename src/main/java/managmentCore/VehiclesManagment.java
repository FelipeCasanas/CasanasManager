/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managmentCore;

/**
 *
 * @author Felipe
 */
public class VehiclesManagment {
    
    private static int carCounter, motorcycleCounter, bikeCounter;
    private static double carIncome, motorcycleIncome, bikeIncome;

    private void askCountingToDatabase() {
        
    }
    
    private void askIncomesToDatabase() {
        
    }
    
    public static int getCarCounter() {
        return carCounter;
    }

    public static void setCarCounter(int carCounter) {
        VehiclesManagment.carCounter = carCounter;
    }

    public static int getMotorcycleCounter() {
        return motorcycleCounter;
    }

    public static void setMotorcycleCounter(int motorcycleCounter) {
        VehiclesManagment.motorcycleCounter = motorcycleCounter;
    }

    public static int getBikeCounter() {
        return bikeCounter;
    }

    public static void setBikeCounter(int bikeCounter) {
        VehiclesManagment.bikeCounter = bikeCounter;
    }

    public static double getCarIncome() {
        return carIncome;
    }

    public static void setCarIncome(double carIncome) {
        VehiclesManagment.carIncome = carIncome;
    }

    public static double getMotorcycleIncome() {
        return motorcycleIncome;
    }

    public static void setMotorcycleIncome(double motorcycleIncome) {
        VehiclesManagment.motorcycleIncome = motorcycleIncome;
    }

    public static double getBikeIncome() {
        return bikeIncome;
    }

    public static void setBikeIncome(double bikeIncome) {
        VehiclesManagment.bikeIncome = bikeIncome;
    }
}
