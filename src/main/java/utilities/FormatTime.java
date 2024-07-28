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
public class FormatTime {

    public String formatFullDate() {
        Date date = new Date();
        SimpleDateFormat doFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatedDate = doFormat.format(date);

        return formatedDate;
    }
    
    public String formatDate() {
        Date date = new Date();
        SimpleDateFormat doFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formatedDate = doFormat.format(date);

        return formatedDate;
    }
    
    public String formatHour() {
        Date date = new Date();
        SimpleDateFormat doFormat = new SimpleDateFormat("HH:mm:ss");
        String formatedDate = doFormat.format(date);

        return formatedDate;
    }
}
