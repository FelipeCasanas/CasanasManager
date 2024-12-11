/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.casanassoftware.administrativemanager;

import generalUtility.SystemAuth;
import userInterface.ErrorUI;
import userInterface.LoginUI;

/**
 *
 * @author Felipe Casanas
 */
public class StartRunning {

    public static void main(String[] args) {
        SystemAuth systemAuth = new SystemAuth();

        if (systemAuth.validateSystemData()) {
            LoginUI login = new LoginUI();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        } else {
            ErrorUI errorUI = new ErrorUI();
            errorUI.setLocationRelativeTo(null);
            errorUI.setVisible(true);
            errorUI.autenticationError();
        }
    }
}
