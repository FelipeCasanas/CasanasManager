/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Felipe
 */
public class SearchDiscriminant {

    public static Boolean[] notEmptyFields(String[] fields) {
        // Crea un array booleano del mismo tamaño que el array de entrada
        Boolean[] binarySelectionEmptyFields = new Boolean[fields.length];

        // Recorre los campos y verifica si están vacíos
        for (int i = 0; i < fields.length; i++) {
            binarySelectionEmptyFields[i] = !fields[i].isEmpty();
        }

        return binarySelectionEmptyFields;
    }

}
