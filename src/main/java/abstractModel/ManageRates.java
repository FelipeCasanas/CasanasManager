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
    
    void addLocalRate(ArrayList<String> ratesName, ArrayList<Double> rates);

    boolean uploadRateToDB(ArrayList<String> newRateIdentifiquer, ArrayList<Double> newRate);

    ArrayList<Object> getRates(int businessType);

    String[] searchRate(String elementName);

    boolean updateRate(Component view, int businessId, String elementName, double newRate);

    boolean deleteRate(Component view, String elementName);

}
