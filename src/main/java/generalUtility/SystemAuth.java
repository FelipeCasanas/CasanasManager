/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generalUtility;

/**
 *
 * @author Felipe
 */
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Connect;
import network.QueryManagment;

public class SystemAuth {

    public String[] getSystemData() {
        try {
            // Obtener el número de serie del disco duro
            String diskSerialNumber = getDiskSerialNumber();
            if (diskSerialNumber == null) {
                throw new RuntimeException("No se pudo obtener el número de serie del disco duro");
            }

            // Obtener el UUID del BIOS
            String biosUUID = getSystemUUID();
            if (biosUUID == null) {
                throw new RuntimeException("No se pudo obtener el UUID del BIOS");
            }

            // Obtener información del sistema operativo
            String osName = System.getProperty("os.name");
            String osVersion = System.getProperty("os.version");
            String osArch = System.getProperty("os.arch");

            if (osName == null || osVersion == null || osArch == null) {
                throw new RuntimeException("No se pudo obtener información del sistema operativo");
            }

            // Devolver los tres valores como un arreglo de strings
            return new String[]{diskSerialNumber, biosUUID, osName + " " + osVersion + " " + osArch};

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getDiskSerialNumber() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String command = os.contains("win")
                    ? "wmic diskdrive get serialnumber"
                    : "udevadm info --query=all --name=/dev/sda | grep ID_SERIAL_SHORT";

            Process process = Runtime.getRuntime().exec(command);
            Scanner scanner = new Scanner(process.getInputStream());
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.toLowerCase().contains("serial")) {
                    scanner.close();
                    return line; // Devuelve el número de serie
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // No se pudo obtener
    }

    private String getSystemUUID() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String command = os.contains("win")
                    ? "wmic csproduct get UUID"
                    : "cat /sys/class/dmi/id/product_uuid";

            Process process = Runtime.getRuntime().exec(command);
            Scanner scanner = new Scanner(process.getInputStream());
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.toLowerCase().contains("uuid")) {
                    scanner.close();
                    return line; // UUID del sistema
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // No se pudo obtener
    }

    public boolean validateSystemData() {
        String[] systemData = getSystemData();  // Obtener los datos del sistema
        if (systemData == null) {
            return false;  // Retorna false si no se pudieron obtener los datos
        }

        String diskSerialNumber = systemData[0];  // Número de serie del disco duro
        String biosUUID = systemData[1];  // UUID del BIOS
        String osInfo = systemData[2];  // Información del sistema operativo

        // Consulta SQL para validar los datos en la base de datos
        String validateSystemQuery = "SELECT 1 FROM devices WHERE disk_serial_number = ? AND bios_uuid = ? AND os_info = ? AND active = 1";

        // Obtener la conexión usando el Singleton Connect y ejecutar la consulta
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement validateSystemPS = link.prepareStatement(validateSystemQuery)) {

            // Establecer los parámetros de la consulta
            validateSystemPS.setString(1, diskSerialNumber);
            validateSystemPS.setString(2, biosUUID);
            validateSystemPS.setString(3, osInfo);

            // Ejecutar la consulta y verificar si el dispositivo está registrado
            try (ResultSet validateSystemRS = validateSystemPS.executeQuery()) {
                return validateSystemRS.next();  // Retorna true si se encuentra el dispositivo
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE,
                    "Error validating system data for disk serial: " + diskSerialNumber
                    + ", BIOS UUID: " + biosUUID + ", and OS info: " + osInfo, ex);  // Manejo de errores
        }

        return false;  // Retorna false si ocurre un error o no se valida el dispositivo
    }

    public void validateUserData(String userEmail, int businessId) {
        String findUserQuery = "SELECT id FROM my_user WHERE email = ?";
        String validateWorkerQuery = "SELECT 1 FROM workers WHERE user_id = ? AND business_id = ?";

        // Obtener la conexión usando el Singleton Connect y ejecutar la consulta
        try (Connection link = Connect.getInstance().getConnection(); PreparedStatement findUserPS = link.prepareStatement(findUserQuery)) {

            // Buscar ID del usuario en la tabla my_user
            findUserPS.setString(1, userEmail);

            try (ResultSet findUserRS = findUserPS.executeQuery()) {
                if (findUserRS.next()) {
                    int userId = findUserRS.getInt("id");

                    // Validar relación en la tabla workers
                    try (PreparedStatement validateWorkerPS = link.prepareStatement(validateWorkerQuery)) {
                        validateWorkerPS.setInt(1, userId);
                        validateWorkerPS.setInt(2, businessId);

                        try (ResultSet validateWorkerRS = validateWorkerPS.executeQuery()) {
                            if (validateWorkerRS.next()) {
                                System.out.println("Access granted. Redirecting to Dashboard...");
                                // Lógica para enviar al Dashboard
                            } else {
                                System.out.println("No valid relationship found between user and business.");
                            }
                        }
                    }
                } else {
                    System.out.println("User not found.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE,
                    "Error validating user data for email: " + userEmail + " and business ID: " + businessId, ex);  // Manejo de errores
        }
    }

}
