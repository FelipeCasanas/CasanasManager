/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processes;

import com.casanassoftware.administrativemanager.red.GetDatabaseData;

/**
 *
 * @author Felipe
 */
public class VehiclesManagment {

    private static int totalVehicles, carsQuantity, motorcyclesQuantity, bikesQuantity;

    
    private void SetQuantities() {
        GetDatabaseData getDatabaseData = new GetDatabaseData();
        String [] logs = getDatabaseData.downloadDayLogs();
        /*
        setCarsQuantity(logs[0]);
        setMotorcyclesQuantity(logs[1]);
        setBikesQuantity(logs[2]);
        */
    }
    
    private void setTotalVehicles(int totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    private void setCarsQuantity(int carsQuantity) {
        this.carsQuantity = carsQuantity;
    }

    private void setMotorcyclesQuantity(int motorcyclesQuantity) {
        this.motorcyclesQuantity = motorcyclesQuantity;
    }

    private void setBikesQuantity(int bikesQuantity) {
        this.bikesQuantity = bikesQuantity;
    }

    public int getTotalVehicles() {
        return totalVehicles;
    }

    public int getCarsQuantity() {
        return carsQuantity;
    }

    public int getMotorcyclesQuantity() {
        return motorcyclesQuantity;
    }

    public int getBikesQuantity() {
        return bikesQuantity;
    }
}
