/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Felipe
 */
public class User {
    private static String id, card_id, name, lastName, birth_day, business_id, businessCategory, email, password, active, admin;

    // Establecer los datos del usuario
    public void setData(String[] userData) {
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

    // Métodos getter y setter para las variables de instancia (no estáticas)
    public static String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    public static String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public static String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public static String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String category) {
        this.businessCategory = category;
    }
}
