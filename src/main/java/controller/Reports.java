/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.util.JRLoader;

public class Reports {

    private final String type;
    private final String color;
    private final String state;
    private final String checkoutState;
    private final String checkinBy;
    private final String checkoutBy;
    private final String ownerId;
    private final String plate;
    private final String companyName;
    private final String companyLogo;
    private final String companyAddress;
    private final String checkoutMessage;

    public Reports(String type, String color, String state, String checkoutState,
            String checkinBy, String checkoutBy, String ownerId, String plate,
            String companyName, String companyLogo, String companyAddress, String checkoutMessage) {
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
        this.checkoutMessage = checkoutMessage;
    }

    /**
     * Compiles a JRXML report file into a compiled JasperReport file.
     *
     * @param jrxmlFile Path to the JRXML file.
     * @param outputFile Path to save the compiled report.
     */
    public void compileReport(String jrxmlFile, String outputFile) {
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile, outputFile);
            System.out.println("Report compiled successfully: " + outputFile);
        } catch (JRException e) {
            System.err.println("Failed to compile report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fills a compiled report with parameters and returns a JasperPrint object.
     *
     * @param compiledReportFile Path to the compiled JasperReport file.
     * @return JasperPrint object containing the filled report, or null if an
     * error occurs.
     */
    public JasperPrint fillReport(String compiledReportFile) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(compiledReportFile);

            Map<String, Object> parameters = prepareParameters();

            return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            System.err.println("Failed to fill report: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Exports a JasperPrint object to a PDF file.
     *
     * @param jasperPrint The JasperPrint object containing the filled report.
     * @param outputFileName Path to save the exported PDF file.
     */
    public void exportReportToPdf(JasperPrint jasperPrint, String outputFileName) {
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
            System.out.println("Report exported to PDF successfully: " + outputFileName);
        } catch (JRException e) {
            System.err.println("Failed to export report to PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays a JasperPrint object in a viewer.
     *
     * @param jasperPrint The JasperPrint object containing the filled report.
     */
    public void displayReport(JasperPrint jasperPrint) {
        if (jasperPrint != null) {
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
        } else {
            System.err.println("Cannot display report: JasperPrint is null.");
        }
    }

    /**
     * Prepares the parameters required for filling the report.
     *
     * @return A Map containing the report parameters.
     */
    private Map<String, Object> prepareParameters() {
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
        parameters.put("checkoutMessage", checkoutMessage);
        return parameters;
    }
}
