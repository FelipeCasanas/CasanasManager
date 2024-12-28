/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import controller.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class BusinessItems {

    public static ArrayList<Object> getColorsName() {
        Connection link = Connect.getInstance().getConnection();

        ArrayList<Object> colorData = new ArrayList<>();
        ArrayList<String> colorID = new ArrayList<>();
        ArrayList<String> color = new ArrayList<>();

        try {
            String colorQuery = "SELECT * FROM color";
            PreparedStatement colorPS = link.prepareStatement(colorQuery);
            ResultSet colorRS = colorPS.executeQuery(colorQuery);

            while (colorRS.next()) {
                colorID.add(colorRS.getString("id"));
                color.add(colorRS.getString("color_name"));
            }

            colorData.add(colorID);
            colorData.add(color);

        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        }

        return colorData;
    }

    public static ArrayList<Object> getRatesName(String business_id) {
        Connection link = Connect.getInstance().getConnection();

        ArrayList<Object> rateData = new ArrayList<>();
        ArrayList<String> rateID = new ArrayList<>();
        ArrayList<String> rate = new ArrayList<>();

        try {
            String rateQuery = "SELECT id, type_name FROM type WHERE business_id = ?";
            PreparedStatement colorPS = link.prepareStatement(rateQuery);
            colorPS.setString(1, User.getBusiness_id());
            ResultSet colorRS = colorPS.executeQuery();

            while (colorRS.next()) {
                rateID.add(colorRS.getString("id"));
                rate.add(colorRS.getString("type_name"));
            }

            rateData.add(rateID);
            rateData.add(rate);

        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rateData;
    }

    public static ArrayList<Object> getStatesName() {
        Connection link = Connect.getInstance().getConnection();

        ArrayList<String> stateID = new ArrayList<>();
        ArrayList<String> state = new ArrayList<>();
        ArrayList<Object> stateData = new ArrayList<>();

        try {
            String stateQuery = "SELECT * FROM state WHERE category_id = ?";
            PreparedStatement statePS = link.prepareStatement(stateQuery);
            statePS.setString(1, User.getBusinessCategory());
            ResultSet stateRS = statePS.executeQuery();

            while (stateRS.next()) {
                stateID.add(stateRS.getString("id"));
                state.add(stateRS.getString("state_name"));
            }

            stateData.add(stateID);
            stateData.add(state);

        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stateData;
    }

    public static List<String[]> getEmployesIDs() {
        List<String[]> employees = new ArrayList<>();
        String query = "SELECT id, name, last_name FROM my_user WHERE business_id = ?";

        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement preparedStatement = link.prepareStatement(query)) {

            // Obtiene el ID del negocio
            int businessId = Integer.parseInt(User.getBusiness_id());
            preparedStatement.setInt(1, businessId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String[] employeeData = new String[3];
                    employeeData[0] = resultSet.getString("id");
                    employeeData[1] = resultSet.getString("name");
                    employeeData[2] = resultSet.getString("last_name");
                    employees.add(employeeData);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los empleados: " + e.getMessage());
            e.printStackTrace();
        }

        return employees;
    }
}
