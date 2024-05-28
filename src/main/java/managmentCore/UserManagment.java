/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managmentCore;

/**
 *
 * @author Felipe
 */
public class UserManagment {

    private static int id, category, active, admin;
    private static String card_id, name, lastName, birth_day, email, password;
    
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserManagment.id = id;
    }

    public static int getCategory() {
        return category;
    }

    public static void setCategory(int category) {
        UserManagment.category = category;
    }

    public static int getActive() {
        return active;
    }

    public static void setActive(int active) {
        UserManagment.active = active;
    }

    public static int getAdmin() {
        return admin;
    }

    public static void setAdmin(int admin) {
        UserManagment.admin = admin;
    }

    public static String getCard_id() {
        return card_id;
    }

    public static void setCard_id(String card_id) {
        UserManagment.card_id = card_id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserManagment.name = name;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        UserManagment.lastName = lastName;
    }

    public static String getBirth_day() {
        return birth_day;
    }

    public static void setBirth_day(String birth_day) {
        UserManagment.birth_day = birth_day;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserManagment.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserManagment.password = password;
    }
}
