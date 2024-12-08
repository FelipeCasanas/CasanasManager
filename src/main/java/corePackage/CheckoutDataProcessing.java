/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corePackage;

/**
 *
 * @author Felipe
 */
public class CheckoutDataProcessing {

    public double[] getCountCheckoutLogs(String[][] checkoutData) {
        // Inicializa el array de resultado para contar carros, motos, bicicletas y sus totales generados
        double[] countedData = new double[8];

        // Recorre el arreglo de checkoutData
        for (String[] data : checkoutData) {
            int index = switch (data[1].toLowerCase()) {
                case "carro" ->
                    0;
                case "moto" ->
                    1;
                case "bicicleta" ->
                    2;
                default ->
                    -1;
            };

            if (index != -1) {
                countedData[index]++;  // Incrementa el contador por tipo de vehículo
                countedData[index + 4] += Double.parseDouble(data[2]);  // Suma el monto al total generado
            }
        }

        // Calcula los totales
        countedData[3] = countedData[0] + countedData[1] + countedData[2];  // Total de vehículos
        countedData[7] = countedData[4] + countedData[5] + countedData[6];  // Total generado

        return countedData;
    }

}
