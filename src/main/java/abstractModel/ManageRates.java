/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package abstractModel;

import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public interface ManageRates {

    void addRate(Component view, int[] searchDiscriminant);

    boolean uploadRateToDB(ArrayList<String> newRateIdentifiquer, ArrayList<Double> newRate);

    ArrayList<Object> getRates(int businessType, int Quantity);

    String[] searchRate(String elementName);

    boolean updateRate(Component view, String elementName, double newRate);

    boolean deleteRate(Component view, String elementName);

}
