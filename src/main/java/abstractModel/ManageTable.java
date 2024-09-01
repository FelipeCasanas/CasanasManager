/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package abstractModel;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Felipe
 */
public interface ManageTable {
    
    void fillTable(String[][] logsData);
    void clearTable(DefaultTableModel tableModel);
    
    
}
