/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import managmentCore.ReportsManagement;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Felipe
 */
public class PrintInvoice {

    public void print(String[] vehicleData) {
        // Ruta al archivo JRXML y ruta de salida para el archivo compilado
        String jrxmlFile = "C:/Users/Felipe/Desktop/InvoiceModel.jrxml";
        String compiledReportFile = "C:/Users/Felipe/Desktop/InvoiceModel.jasper";

        // Datos para llenar el reporte
        String type = vehicleData[1].toUpperCase();
        String color = vehicleData[2].toUpperCase();
        String state = vehicleData[3].toUpperCase();
        String checkoutState = vehicleData[4].toUpperCase();
        String checkinBy = vehicleData[5].toUpperCase();
        String checkoutBy = vehicleData[6].toUpperCase();
        String ownerId = vehicleData[7];
        String plate = vehicleData[8].toUpperCase();
        String companyName = "Mi Empresa S.A.";                     //NOMBRE EMPRESA CLIENTE
        String companyLogo = "C:/Users/Felipe/Desktop/icono.jpg";   //LOGO DE COMPAÑIA A DEFINIR
        String companyAddress = "Calle Falsa 123";                  //DIRECCION DE PARQUEADERO CLIENTE

        // Crear una instancia de ReportsManagement
        ReportsManagement reportsManagement = new ReportsManagement(type, color, state, checkoutState,
                checkinBy, checkoutBy, ownerId, plate, companyName, companyLogo, companyAddress);

        // Compilar el reporte
        reportsManagement.compileReport(jrxmlFile, compiledReportFile);

        // Llenar el reporte
        JasperPrint jasperPrint = reportsManagement.fillReport(compiledReportFile);

        // Exportar el reporte a PDF y mostrarlo
        if (jasperPrint != null) {
            String pdfOutputFile = "C:/Users/Felipe/Desktop/Invoice.pdf";
            reportsManagement.exportReportToPdf(jasperPrint, pdfOutputFile);
            reportsManagement.displayReport(jasperPrint);
        } else {
            System.out.println("Error: No se pudo generar el reporte.");
        }
    }
}
