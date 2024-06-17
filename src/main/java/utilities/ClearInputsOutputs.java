/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import managmentCore.UserManagment;

/**
 *
 * @author Felipe
 */
public class ClearInputsOutputs {
    
    
    public void clearUserData() {
        UserManagment userManagment = new UserManagment();
        userManagment.setId(0);
        UserManagment.setCard_id("");
        UserManagment.setName("");
        UserManagment.setLastName("");
        UserManagment.setBirth_day("");
        UserManagment.setCategory(0);
        UserManagment.setEmail("");
        UserManagment.setPassword("");
        UserManagment.setActive(0);
        UserManagment.setAdmin(0);
    }
}
