/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generalUtility;

import corePackage.ReportsManagement;
import net.sf.jasperreports.engine.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Connect;

public class PrintingMethods {

    private static String[] businessData = new String[14];

    public void getBusinessData() {
        Connection link = Connect.getInstance().getConnection(); 

        try {
            String queryBusinnessData = "SELECT b1.*, im1.icon, im1.jrxml_file, im1.jasper_model "
                    + "FROM business b1 "
                    + "INNER JOIN invoice_model im1 "
                    + "ON b1.id = im1.business_id";
            PreparedStatement businessPS = link.prepareStatement(queryBusinnessData);
            ResultSet businessRS = businessPS.executeQuery();

            while (businessRS.next()) {
                this.businessData[0] = businessRS.getString("id");
                this.businessData[1] = businessRS.getString("category");
                this.businessData[2] = businessRS.getString("name");
                this.businessData[3] = businessRS.getString("owner_name");
                this.businessData[4] = businessRS.getString("phone_number");
                this.businessData[5] = businessRS.getString("email");
                this.businessData[6] = businessRS.getString("address");
                this.businessData[7] = businessRS.getString("city");
                this.businessData[8] = businessRS.getString("state");
                this.businessData[9] = businessRS.getString("country");
                this.businessData[10] = businessRS.getString("created_at");
                this.businessData[11] = businessRS.getString("im1.icon");
                this.businessData[12] = businessRS.getString("im1.jrxml_file");
                this.businessData[13] = businessRS.getString("im1.jasper_model");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PrintingMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(String[] vehicleData) {
        // Rutas predefinidas
        String jrxmlFile = "src/main/resources/InvoiceModel.jrxml";
        String compiledReportFile = "src/main/resources/InvoiceModel.jasper";
        String companyLogo = "src/main/resources/icon.jpg";

        // Datos para llenar el reporte
        String type = vehicleData[9].toUpperCase();
        String color = vehicleData[1].toUpperCase();
        String state = vehicleData[2].toUpperCase();
        String checkoutState = vehicleData[3].toUpperCase();
        String checkinBy = vehicleData[4].toUpperCase();
        String checkoutBy = vehicleData[5].toUpperCase();
        String ownerId = vehicleData[8];
        String plate = vehicleData[7].toUpperCase();
        String companyName = this.businessData[2];                     // NOMBRE EMPRESA CLIENTE
        String companyAddress = this.businessData[6];                  // DIRECCION DE PARQUEADERO CLIENTE

        // Crear una objeto de ReportsManagement
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
