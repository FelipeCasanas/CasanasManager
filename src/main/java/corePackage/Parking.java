/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corePackage;

import abstractModel.ManageBussiness;
import network.QueryManagment;
import java.awt.Component;
import javax.swing.JOptionPane;
import generalUtility.TimeMethods;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class Parking extends ManageBussiness {

    Rates rates = new Rates();
    TimeMethods formatter = new TimeMethods();
    QueryManagment queryManagment = new QueryManagment();
    User userManagment = new User();

    public Double[] getParkingRates() {
        Double[] returnedRates = new Double[3];

        //Se inicializa ArrayList que va a recibir 2 ArrayList, uno con los nombres(String) y otro con las tarifas(Double)
        ArrayList<Object> ratesList = new ArrayList<>();

        //Llama a metodo en rates para obtener las tarifas
        ratesList = rates.getRates(1, 0);

        //Parsea ArrayList de objeto a sus tipos string y double respectivamente para poder acceder a su informacion
        ArrayList<String> ratesName = (ArrayList<String>) ratesList.get(0);
        ArrayList<Double> ratesAmount = (ArrayList<Double>) ratesList.get(1);

        //En ratesName busca si existe el nombre del elemento, si existe trata de obtenerlo
        if (ratesName.contains("carro")) {
            //Guarda el valor de rates en la posicion donde ratesName conside con el tipo de vehiculo de carro
            returnedRates[0] = ratesAmount.get(ratesName.indexOf("carro"));
        } else if (ratesName.contains("moto")) {
            //Guarda el valor de rates en la posicion donde ratesName conside con el tipo de vehiculo de moto
            returnedRates[1] = ratesAmount.get(ratesName.indexOf("moto"));
        } else if (ratesName.contains("bicicleta")) {
            //Guarda el valor de rates en la posicion donde ratesName conside con el tipo de vehiculo de bicicleta
            returnedRates[2] = ratesAmount.get(ratesName.indexOf("bicicleta"));
        }

        return returnedRates;
    }

    @Override
    public void checkIn(Component view, String[] elementArguments, String[] auxuliarData) {

        try {
            //Busca en DB si existe una coincidencia de vehiculo (MISMA PLACA Y QUE NO HAYA SALIDO)
            boolean vehicleExists = queryManagment.vehicleStillHere(elementArguments[4]);

            //Ejecuta si el vehiculo no se encuentra en el parqueadero
            if (!vehicleExists) {
                int checkIn = JOptionPane.showConfirmDialog(view, "Esta seguro que desea hacer el ingreso?");

                //Se inicia proceso para ingresar el vehiculo
                if (checkIn == 0) {
                    //Obtiene el id del trabajador en turno
                    String workerId = userManagment.getId();

                    //Obtiene la fecha y hora actual (EN ESTE CASO PARA CHECKIN)
                    String formattedDate = formatter.formatFullDate();

                    //Intenta hacer consulta de insercion, si retorna verdadero se logro; en caco contrario no
                    boolean vehicleInserted = queryManagment.insertVehicle(elementArguments, workerId, formattedDate);

                    if (vehicleInserted) {
                        JOptionPane.showMessageDialog(view, "Se realizo el ingreso");
                    } else {
                        JOptionPane.showMessageDialog(view, "ERROR, no se pudo ingresar el vehiculo");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(view, "El vehiculo ya se encuentra en el parqueadero");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean checkOut(Component view, String[] elementArguments, String[] auxuliarData) {
        boolean checkoutCompleted = false;

        try {
            boolean stillHere = queryManagment.vehicleStillHere(elementArguments[2]);

            //Si sigue en parqueadero sigue proceso; Si no, muestra mensaje que vehiculo ya salio del parquedero
            if (stillHere) {
                //Pide la informacion del vehiculo(TIPO VEHICULO) y lo envia a confirmDeparture()
                String[] vehicleData = queryManagment.searchVehicle("plate", elementArguments[2]);

                //PENDIENTE REFACTORIZAR
                //Instancia Double y pregunta si va a cobrar tarifa regular
                double parkingPrice = 0;
                //int rateType = JOptionPane.showConfirmDialog(view, "Desea cobrar la tarifa regular?");
                parkingPrice = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el valor a cobrar"));

                //Cobra tarifa regular 
                /*
                if (rateType == 0) {
                    //Envia a metodo especifico para hacer proceso de obtener tarifas de vehiculos
                    parkingPrice = getParkingRates(parkingPrice);
                } else if (rateType == 1) {
                    parkingPrice = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el valor a cobrar"));
                }
                 */
                //Obtiene la fecha y hora parseada a String
                String formattedDate = formatter.formatFullDate();

                //Obtiene el ID del trabajador que tiene sesion iniciada y lo convierte a String
                String userId = String.valueOf(userManagment.getId());

                //Ejecuta el Checkout con los datos necesarios. (ID vehiculo, estado salida(El que esta en el combo box), quien realiza salida, fecha y hora salida)
                boolean checkout = queryManagment.checkOutVehicle(vehicleData[0], elementArguments[0], userId, formattedDate, parkingPrice);

                if (checkout) {
                    JOptionPane.showMessageDialog(view, "SALIDA COMPLETADA");
                    checkoutCompleted = true;
                } else {
                    JOptionPane.showMessageDialog(view, "No se pudo realizar la salida, intentelo de nuevo");
                }

            } else {
                JOptionPane.showMessageDialog(view, "El vehiculo no se encuentra en el parqueadero");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        //  PENDIENTE REVISAR
        return checkoutCompleted;
    }

    @Override
    public String[] search(String[] searchArguments) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int[] countLogs(String[] countElements) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean setIncome(Double income, String elementPosition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double[] getIncome(String searchParam) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
