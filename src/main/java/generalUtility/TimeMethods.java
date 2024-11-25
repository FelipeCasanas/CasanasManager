/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generalUtility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Felipe
 */
public class TimeMethods {
    
    public static String formatFullDate() {
        Date date = new Date();
        SimpleDateFormat doFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return doFormat.format(date);
    }
    
    public static String formatDate() {
        Date date = new Date();
        SimpleDateFormat doFormat = new SimpleDateFormat("yyyy/MM/dd");
        return doFormat.format(date);
    }
    
    public static String formatHour() {
        Date date = new Date();
        SimpleDateFormat doFormat = new SimpleDateFormat("HH:mm:ss");
        return doFormat.format(date);
    }
}
