/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managmentCore;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.util.JRLoader;
import utilities.FormatTime;

public class ReportsManagement {

    private String type;
    private String color;
    private String state;
    private String checkoutState;
    private String checkinBy;
    private String checkoutBy;
    private String ownerId;
    private String plate;
    private String companyName;
    private String companyLogo;
    private String companyAddress;

    public ReportsManagement(String type, String color, String state, String checkoutState,
            String checkinBy, String checkoutBy, String ownerId, String plate,
            String companyName, String companyLogo, String companyAddress) {
        this.type = type;
        this.color = color;
        this.state = state;
        this.checkoutState = checkoutState;
        this.checkinBy = checkinBy;
        this.checkoutBy = checkoutBy;
        this.ownerId = ownerId;
        this.plate = plate;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.companyAddress = companyAddress;
    }

    public void compileReport(String jrxmlFile, String outputFile) {
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile, outputFile);
            System.out.println("Report compiled successfully: " + outputFile);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public JasperPrint fillReport(String compiledReportFile) {
        try {
            FormatTime formatTime = new FormatTime();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(compiledReportFile);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("type", type);
            parameters.put("color", color);
            parameters.put("state", state);
            parameters.put("checkoutState", checkoutState);
            parameters.put("checkinBy", checkinBy);
            parameters.put("checkoutBy", checkoutBy);
            parameters.put("ownerId", ownerId);
            parameters.put("plate", plate);
            parameters.put("companyName", companyName);
            parameters.put("companyLogo", companyLogo);
            parameters.put("companyAddress", companyAddress);
            parameters.put("printDate", new Date());
            parameters.put("printHour", new Date());

            return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void exportReportToPdf(JasperPrint jasperPrint, String outputFileName) {
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
            System.out.println("Report exported to PDF successfully: " + outputFileName);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void displayReport(JasperPrint jasperPrint) {
        JasperViewer viewer = new JasperViewer(jasperPrint, false);
        viewer.setVisible(true);
    }
}
