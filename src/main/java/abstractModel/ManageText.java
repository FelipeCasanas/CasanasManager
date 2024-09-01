/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package abstractModel;

import java.awt.Component;

/**
 *
 * @author Felipe
 */
public interface ManageText {
    
    boolean compare(String[] textInput, int operationToDo);
    boolean validate(Component view, String[] toValidate, String[] auxiliar, int limit);
    String[] separate(String[] textInput, int operationToDo);
    void cleanString(int operationToDo);
    
}
