/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generalUtility;

/**
 *
 * @author Felipe
 */
public class SearchDiscriminant {

    //Recibe los datos a comprobar si estan vacios y retorna array booleano; true para datos que se actualizaran
    public Boolean[] notEmptyFields(String[] fields) {
        
        //Obtiene el largo de la entrada de fields y define el inicio del bucle en 0
        int inputSize = fields.length, range = 0;

        //Array booleano para guardar los campos que se actualizaran
        Boolean[] binarySelectionEmptyFields = new Boolean[inputSize];

        //Ejecuta mientras sea menor al numero de entradas de campos
        do {
            //Si field no esta vacio retorna verdadero; Si no, falso
            if (!fields[range].isEmpty()) {
                binarySelectionEmptyFields[range] = true;
            } else {
                binarySelectionEmptyFields[range] = false;
            }
            
            //Aumenta el valor actual del bucle en 1
            range++;
        } while (range < fields.length);

        return binarySelectionEmptyFields;
    }
}
