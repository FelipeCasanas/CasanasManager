/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corePackage;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import network.QueryManagment;

/**
 *
 * @author Felipe
 */
public class Rates extends network.Connect implements abstractModel.ManageRates {

    //Declara 2 listas, el primero String para darle nombre a la tarifa. El segundo tipo Double para el valor de la tarifa
    private static ArrayList<String> newRateName = new ArrayList<>();
    private static ArrayList<Double> rate = new ArrayList<>();

    //Instancia la clase de consultas
    QueryManagment queryManagment = new QueryManagment();

    //Recibe el componente desde el que ha sido llamado el metodo
    @Override
    public void addRate(Component view, int[] searchDiscriminant) {
        boolean stop = false;

        //Declara 2 listas, guardan los valores temporalmente hasta que sean integradas a la DB
        ArrayList<String> newRateName = new ArrayList<>();
        ArrayList<Double> newRate = new ArrayList<>();

        while (stop == false) {
            //Obtiene el nombre de la nueva tarifa
            String newElementName = JOptionPane.showInputDialog(view, "Como se llamara el nuevo elemento?");

            //Obtiene el valor de la nueva tarifa
            String newElementValue = JOptionPane.showInputDialog(view, "Que valor tendra el nuevo elemento?");

            //Valida que ambos campos no esten vacios; Si alguno lo esta muestra mensaje indicandolo
            if (newElementName.length() > 0 && newElementValue.length() > 0) {
                //Si no existe tarifa con ese nombre entoces la añade; Si no, muestra mensaje indicando que ya existe
                if (!newRateName.contains(newElementName)) {
                    newRateName.add(newElementName);
                    newRate.add(Double.parseDouble(newElementValue));
                } else {
                    JOptionPane.showMessageDialog(view, "Ya existe un elemento con ese nombre");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Alguno de los campos esta vacio");
            }

            //Pregunta si usuario quiere seguir añadiento elementos
            int continueAdding = JOptionPane.showConfirmDialog(view, "Quiere continuar añadiendo elementos");

            //Si valor de continueAdding es diferente de 1 entonces deja de iterar bucle para añadir registros
            if (continueAdding == 0) {
                stop = true;
            }
        }

        //Llama metodo para subir tarifas a base de datos
        uploadRateToDB(newRateName, newRate);
    }
    
    @Override
    public void addLocalRate(ArrayList<String> ratesName, ArrayList<Double> rates) {
        this.newRateName = ratesName;
        this.rate = rates;
    }

    @Override
    public boolean uploadRateToDB(ArrayList<String> newRateName, ArrayList<Double> newRate) {
        //Abre la conexion a la base de datos
        this.connect();
        Connection link = getConnection();

        //Variable temporal que indica el id del negocio que esta funcionando en este momento
        int businessID = 1;

        //Retorna true si la insercion se ejecuta correctamente
        //Si algun dato no se actualiza retorna false y se hace rollback
        boolean upload = true;

        try {
            //String con consulta para insertar nuevas tarifas
            String insertRate = "INSERT INTO price(business_id, rate_name, rate_amount) VALUES (?, ?, ?);";

            for (int i = 0; i < newRateName.size(); i++) {
                //Se crea PreparedStatement y en cada posicion inserta el valor correspondiente en la posicion de i para cada elemento
                PreparedStatement inserRatePS = link.prepareStatement(insertRate);
                inserRatePS.setInt(1, businessID);
                inserRatePS.setString(2, newRateName.get(i));
                inserRatePS.setDouble(3, newRate.get(i));

                //Ejecuta consulta de insercion
                int inserted = inserRatePS.executeUpdate();

                //Si consulta retorno 0 entonces upload = false
                if (inserted == 0) {
                    upload = false;
                }

                //Cierra la consulta
                inserRatePS.close();
            }

            //Valida que upload sea true; Si es false significa que en algun punto algun elemento no se inserto y hace rollback
            if (upload == false) {
                link.rollback();
            }

            //Cierra la conexion a la base de datos
            this.closeConnection();
        } catch (SQLException ex) {
            try {
                link.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Rates.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        //Retorna las tarifas obtenidas
        return upload;
    }

    //searchDiscriminant[0] tiene el discriminante de id del negocio que solicita las tarifas
    //searchDiscriminant[1] determina la cantidad de tarifas que se pediran a la DB, -1 deslimita (PENDIENTE)
    @Override
    public ArrayList<Object> getRates(int businessType, int Quantity) {
        //Abre la conexion a la base de datos
        this.connect();
        Connection link = getConnection();

        //Declara lista para guardar el nombre de las tarifas
        ArrayList<String> ratesName = new ArrayList<>();

        //Declara lista para guardar las tarifas
        ArrayList<Double> rates = new ArrayList<>();

        try {
            String queryRates = "SELECT * FROM price WHERE business_id = ?";
            PreparedStatement ratesPS = link.prepareStatement(queryRates);
            ratesPS.setString(1, String.valueOf(businessType));
            ResultSet ratesRS = ratesPS.executeQuery();

            //Acumula tarifas en la lista en la columna asignada (PENDIENTE ASIGNAR LA COLUMNA CORRECTA)
            while (ratesRS.next()) {
                ratesName.add(ratesRS.getString("rate_name")); //PENDIENTE CONFIRMAR SI ES ESE NOMBRE
                rates.add(ratesRS.getDouble("rate_amount"));  //PENDIENTE CONFIRMAR SI ES ESE NOMBRE
            }

            //Asigna valores de ArrayList´s locales a ArrayList´s globales
            addLocalRate(ratesName, rates);
            
            //Iicializa ArrayList que guarda los otros 2 ArrayList (ratesName y rates)
            ArrayList<Object> ratesData = new ArrayList<>();
            ratesData.add(ratesName);
            ratesData.add(rates);

            //Cierra el ResultSet
            ratesRS.close();

            //Cierra la consulta
            ratesPS.close();

            //Cierra la conexion a la base de datos
            this.closeConnection();

            //Retorna las tarifas obtenidas
            return ratesData;
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    //Recibe como argumento el nombre del elemento a buscar
    @Override
    public String[] searchRate(String elementName) {
        //Busca el nombre del elemento ingresado, si lo encuentra lo guarda en elementIndex
        int elementIndex = newRateName.indexOf(elementName);
        String[] ratesData = new String[3];

        //Si elementIndex es diferente a -1 elimina el elemento en esa posicion en rateIdentifiquer y rate
        if (elementIndex != -1) {
            //ratesData[0] contiene el codigo de la tarifa
            ratesData[0] = String.valueOf(elementIndex);

            //ratesData[1] contiene el nombre de la tarifa
            ratesData[1] = newRateName.get(elementIndex);

            //ratesData[2] contiene el valor de la tarifa
            ratesData[2] = String.valueOf(rate.get(elementIndex));

            //Devuelve el array completo
            return ratesData;
        } else {
            return null;
        }
    }

    //PENDIENTE 
    @Override
    public boolean updateRate(Component view, int businessId, String elementName, double newRate) {
        //Abre la conexion a la base de datos
        this.connect();
        Connection link = getConnection();

        //Declara booleano que indica si se logro actualizar la tarifa
        boolean rateUpdated = false;

        //Busca los datos del elemento ingresado, si lo encuentra guarda su informacion
        //String[] ratesData = searchRate(elementName);

        //Ejecuta cosulta a DB para actualizar la tarifa. Si la actualizacion es exitosa devuelve true
        try {
            String updateRateQuery = "UPDATE price SET rate_amount = ? WHERE business_id = ? AND rate_name = ?";    //rate_name es temporal, despues sera id
            PreparedStatement updateRatePS = link.prepareStatement(updateRateQuery);
            updateRatePS.setDouble(1, newRate);
            updateRatePS.setInt(2, businessId);
            updateRatePS.setString(3, elementName/*ratesData[0]*/);     //Esto es temporal mientras hago que los ArrayList guarden los datos
            int executedUpdate = updateRatePS.executeUpdate();

            if (executedUpdate == 1) {
                rateUpdated = true;
                link.commit();
            } else {
                link.rollback();
            }

            //Cierra la consulta
            updateRatePS.close();

            //Cierra la conexion a la base de datos
            this.closeConnection();

        } catch (SQLException ex) {
            Logger.getLogger(Rates.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rateUpdated;
    }

    //Recibe como argumentos el componente donde se esta llamando el metodo y el nombre del elemento a eliminar
    @Override
    public boolean deleteRate(Component view, String elementName) {
        //Valor booleano que retornara la funcion para confirmar que se realizo el proceso
        boolean remove = false;

        //Busca el nombre del elemento ingresado, si lo encuentra lo guarda en elementIndex
        int elementIndex = newRateName.indexOf(elementName);
        String[] searchResult = searchRate(elementName);

        //Si elementIndex es diferente a -1 elimina el elemento en esa posicion en rateIdentifiquer y rate
        if (searchResult != null) {
            newRateName.remove(elementIndex);
            rate.remove(elementIndex);
            remove = true;
        } else {
            JOptionPane.showMessageDialog(view, "Elemento no encontrado");
            remove = false;
        }

        return remove;
    }

}
