/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Felipe
 */
public class FieldsToUpdate {

    public Boolean[] getFields(String[] fields) {
        int inputSize = fields.length, range = 0;
        Boolean[] willBeUpdated = new Boolean[inputSize];

        do {
            if (!fields[range].isEmpty()) {
                willBeUpdated[range] = true;
            } else {
                willBeUpdated[range] = false;
            }
        } while (range < fields.length);

        return willBeUpdated;
    }
}
