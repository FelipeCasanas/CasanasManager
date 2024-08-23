/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managmentCore;

/**
 *
 * @author Felipe
 */
public class CheckoutDataProcessing {

    public double[] getCountCheckoutLogs(String[][] checkoutData) {
        /*
        countedData:
            0: conteo de carros
            1: conteo de motos
            2: conteo de bicicletas
            3: conteo total
            4: total generado carros
            5: total generado motos
            6: total generado bicicletas
            7: total generado por todos
         */

 /*
            1. data[i]  (Array 7 posiciones)
            2. bucle mientras menor a largo array
                1. si checkoutData[i][1] == "Carro" ? countedData[0]++ y countedData[4] += checkoutData[i][2] 
                2. si checkoutData[i][1] == "Moto" ? countedData[1]++ y countedData[5] += checkoutData[i][2] 
                3. si checkoutData[i][1] == "Bicicleta" ? countedData[2]++ y countedData[6] += checkoutData[i][2] 
            3. countedData[3] = checkoutLogsData.length o countedData[0] + countedData[1] + countedData[2]  (Suma total vehiculos)
            4. countedData[7] = countedData[4] + countedData[5] + countedData[6]    (Total dinero generado por vehiculos)
         */
        double[] countedData = new double[8];
        int range = 0;

        for (int i = 0; i < 7; i++) {
            countedData[i] = 0;
        }

        while (range < checkoutData.length) {
            if (checkoutData[range][1].equals("carro")) {
                countedData[0]++;
                countedData[4] += Double.parseDouble(checkoutData[range][2]);
            }

            if (checkoutData[range][1].equals("moto")) {
                countedData[1]++;
                countedData[5] += Double.parseDouble(checkoutData[range][2]);
            }

            if (checkoutData[range][1].equals("bicicleta")) {
                countedData[2]++;
                countedData[6] += Double.parseDouble(checkoutData[range][2]);
            }

            range++;
        }

        countedData[3] = countedData[0] + countedData[1] + countedData[2];
        countedData[7] = countedData[4] + countedData[5] + countedData[6];

        return countedData;
    }
}
