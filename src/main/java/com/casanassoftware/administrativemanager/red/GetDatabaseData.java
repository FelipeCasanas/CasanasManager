/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.casanassoftware.administrativemanager.red;

/**
 *
 * @author Felipe
 */
public class GetDatabaseData {
    
    public String[] downloadUserData() {
        String [] userData = {"", "", ""};
        return userData;
    }
    
    public String[] downloadDayLogs() {        
        //ESTE METODO SE EJECUTA CUANDO ENTREN A CUADRE DE CAJA
        //AL ENTRAR SE DESACTIVA LSO BOTONES DE CHECKIN Y CHECKOUT
        //PARA VOLVER A ACTIVARLOS DEBEN DAR EN BOTON DE TODO CORRECTO EN CUADRE DE CAJA
        
        //Pide al servidor los registros del dia por cada tipo de vehiculo y los establece
        //Retorna toda la informacion del usuario en el dia de hoy
        //Se usan metodos set para establecer esos valores en los atributos de la clase
        //Se establece esa informacion en los label correspondientes para mostrarla
        //(De poderse agregar grafica se agrega)
        
        String [] dayLogs = {};
        return dayLogs;
    }
    
    public String[] downloadAllLogs() {
        String [] allLogs = {};
        return allLogs;
    }
    
}
