/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package abstractModel;

import java.sql.ResultSet;

/**
 *
 * @author Felipe
 */
public interface ManageUsers {
    
    boolean authUser(String email, String password);  //Valida parametros correctos y devuelve true(Autenticado) o false(No autenticado)
    String[] getUserData(String id, int permissions); //Id der la cuenta a buscar, permisos de quien busca la informacion
    boolean modifyUser(String[] arguments, int operationToDo); //operationToDo: (1)Personal (2)Rol (3)Preferencias
    
}
