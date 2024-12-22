/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Felipe
 */
public class TimeMethods {

    public static String formatFullDate() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    public static String formatDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    public static String formatHour() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

}
