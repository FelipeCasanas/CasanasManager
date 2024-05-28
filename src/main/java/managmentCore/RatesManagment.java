/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managmentCore;

/**
 *
 * @author Felipe
 */
public class RatesManagment {
    
    private static double carRate, motorcycleRate, bikeRate;
    
    
    private void askRatesToDatabase() {
        
    }
    
    public double getCarRate() {
        return carRate;
    }
    
    public double getMotorcycle() {
        return motorcycleRate;
    }
    
    public double getBikeRate() {
        return bikeRate;
    }
    
    //METODOS USADOS PARA ESTABLECER LA INFORMACION OBTENIDA DE LA BASE DE DATOS
    public void setCarRate(double carRate) {
        this.carRate = carRate;
    }

    public void setMotorcycleRate(double motorcycleRate) {
        this.motorcycleRate = motorcycleRate;
    }

    public void setBikeRate(double bikeRate) {
        this.bikeRate = bikeRate;
    }
}
