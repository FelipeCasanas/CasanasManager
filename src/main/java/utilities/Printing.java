/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import controller.Reports;
import controller.User;
import net.sf.jasperreports.engine.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Connect;

public class Printing {

    private static String[] businessData = new String[11];

    public void getBusinessData() {
        Connection link = Connect.getInstance().getConnection();

        try {
            String queryBusinessData = "SELECT * "
                    + "FROM business WHERE id = ?";
            PreparedStatement businessPS = link.prepareStatement(queryBusinessData);
            businessPS.setString(1, User.getBusiness_id());
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
            }

        } catch (SQLException ex) {
            Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(String[] itemData) {
        // Rutas predefinidas
        String jrxmlFile = "src/main/resources/InvoiceModel.jrxml";
        String compiledReportFile = "src/main/resources/InvoiceModel.jasper";
        String companyLogo = "src/main/resources/icon.jpg";

        String checkoutMessage;
        if (User.getBusinessCategory().equals("1")) {
            checkoutMessage = "¡Que tenga buen viaje!";
        } else {
            checkoutMessage = "¡Gracias por su compra!";
        }

        // Datos para llenar el reporte
        String type = itemData[9].toUpperCase();
        String color = itemData[1].toUpperCase();
        String state = itemData[2].toUpperCase();
        String checkoutState = itemData[3].toUpperCase();
        String checkinBy = itemData[4].toUpperCase();
        String checkoutBy = itemData[5].toUpperCase();
        String ownerId = itemData[8];
        String identifiquer = itemData[7].toUpperCase();
        String companyName = this.businessData[2]; // Nombre de la empresa
        String companyAddress = this.businessData[6]; // Dirección de la empresa

        // Crear un objeto de Reports
        Reports reportsManagement = new Reports(type, color, state, checkoutState,
                checkinBy, checkoutBy, ownerId, identifiquer, companyName, companyLogo, companyAddress, checkoutMessage);

        // Compilar el reporte (si aún no está compilado)
        reportsManagement.compileReport(jrxmlFile, compiledReportFile);

        // Llenar el reporte
        JasperPrint filledReport = reportsManagement.fillReport(compiledReportFile);
        if (filledReport != null) {
            // Exportar a PDF
            String outputPdfFile = "src/main/invoices/Invoice_" + identifiquer.toLowerCase() + ".pdf";
            reportsManagement.exportReportToPdf(filledReport, outputPdfFile);

            // Mostrar el reporte
            reportsManagement.displayReport(filledReport);
        } else {
            System.err.println("No se pudo llenar el reporte.");
        }
    }

}
