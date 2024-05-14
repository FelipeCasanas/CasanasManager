/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processes;

/**
 *
 * @author Felipe
 */
public class RatesManagment {
    
    private static double carRate, motorcycleRate, bikeRate;
    
    
    public void setCarRate(double carRate) {
        this.carRate = carRate;
    }

    public void setMotorcycleRate(double motorcycleRate) {
        this.motorcycleRate = motorcycleRate;
    }

    public void setBikeRate(double bikeRate) {
        this.bikeRate = bikeRate;
    }
    
    protected double getCarRate() {
        return carRate;
    }
    
    protected double getMotorcycle() {
        return motorcycleRate;
    }
    
    protected double getBikeRate() {
        return bikeRate;
    }
    
}
