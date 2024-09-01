/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Felipe
 */
public class User extends network.Connect implements abstractModel.ManageUsers {

    private static String id, card_id, name, lastName, birth_day, business_id, email, password, active, admin;


    Connection link = this.getConnection();
    
    
    //PENDIENTE HACER INTERFAZ PARA CERRAR CONEXIONES
    //METODOS INTERFAZ MANAGEUSERS
    @Override
    public boolean authUser(String email, String password) {
        this.connect();
        boolean isLogged = false;

        try {
            String query = "SELECT id from my_user WHERE email = ? AND password = ? AND active = ?;";
            PreparedStatement loginPS = link.prepareStatement(query);
            loginPS.setString(1, email);
            loginPS.setString(2, password);
            loginPS.setString(3, "1");
            ResultSet loginResult = loginPS.executeQuery();

            if (loginResult.next()) {
                isLogged = true;
            }

            loginPS.close();
            loginResult.close();
            this.closeConnection();

            return isLogged;
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String[] getUserData(String email, int permissions) {
        this.connect();

        try {
            if (permissions == 1) {

                String[] userData = new String[10];
                String query = "SELECT * from my_user WHERE email = ?;";
                PreparedStatement loginPS = link.prepareStatement(query);
                loginPS.setString(1, email);
                ResultSet queryResult = loginPS.executeQuery();

                if (queryResult.next()) {
                    userData[0] = queryResult.getString("id");
                    userData[1] = queryResult.getString("card_id");
                    userData[2] = queryResult.getString("name");
                    userData[3] = queryResult.getString("last_name");
                    userData[4] = queryResult.getString("birth_day");
                    userData[5] = queryResult.getString("business_id");
                    userData[6] = queryResult.getString("email");
                    userData[7] = queryResult.getString("password");
                    userData[8] = queryResult.getString("active");
                    userData[9] = queryResult.getString("admin");
                }

                loginPS.close();
                queryResult.close();
                this.closeConnection();
                return userData;
            } else if (permissions == 0) {
                String[] userData = new String[5];
                String query = "SELECT name, last_name, business_id, email, active FROM my_user WHERE id = ?;";
                PreparedStatement loginPS = link.prepareStatement(query);
                loginPS.setString(1, id);
                ResultSet queryResult = loginPS.executeQuery();

                if (queryResult.next()) {
                    userData[0] = queryResult.getString("name");
                    userData[1] = queryResult.getString("last_name");
                    userData[2] = queryResult.getString("business_id");
                    userData[3] = queryResult.getString("email");
                    userData[4] = queryResult.getString("active");
                }

                loginPS.close();
                queryResult.close();
                this.closeConnection();
                return userData;

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void setUserData(String[] userData) {
        setId(userData[0]);
        setCard_id(userData[1]);
        setName(userData[2]);
        setLastName(userData[3]);
        setBirth_day(userData[4]);
        setBusiness_id(userData[5]);
        setEmail(userData[6]);
        setPassword(userData[7]);
        setActive(userData[8]);
        setAdmin(userData[9]);
    }
    

    @Override
    public boolean modifyUser(String[] arguments, int operationToDo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //GETTERS Y SETTERS
    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    public static String getCard_id() {
        return card_id;
    }

    public static void setCard_id(String card_id) {
        User.card_id = card_id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        User.lastName = lastName;
    }

    public static String getBirth_day() {
        return birth_day;
    }

    public static void setBirth_day(String birth_day) {
        User.birth_day = birth_day;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getBusiness_id() {
        return business_id;
    }

    public static void setBusiness_id(String business_id) {
        User.business_id = business_id;
    }

    public static String getActive() {
        return active;
    }

    public static void setActive(String active) {
        User.active = active;
    }

    public static String getAdmin() {
        return admin;
    }

    public static void setAdmin(String admin) {
        User.admin = admin;
    }

}
