/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package utilities;

import utilities.SystemAuth;
import ui.ErrorUI;
import ui.LoginUI;

/**
 *
 * @author Felipe Casanas
 */
public class Main {

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
