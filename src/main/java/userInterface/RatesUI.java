/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userInterface;

import corePackage.Parking;
import javax.swing.JOptionPane;
import corePackage.Rates;
import corePackage.User;
import java.util.ArrayList;
import network.QueryManagment;

/**
 *
 * @author Felipe
 */
public class RatesUI extends javax.swing.JFrame {

    Parking parking = new Parking();

    public RatesUI() {
        initComponents();
        
        setRateTypeSelector();
        ArrayList<Double> ratesData = parking.getRates();
        printRates(ratesData);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Rates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ratesTitleLabel = new javax.swing.JLabel();
        ratesGoBackButton = new javax.swing.JButton();
        developerLabel = new javax.swing.JLabel();
        updateRate = new javax.swing.JButton();
        rateSelector = new javax.swing.JComboBox<>();
        rateChangeStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        ratesTitleLabel.setFont(new java.awt.Font("Gill Sans MT Condensed", 1, 20)); // NOI18N
        ratesTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ratesTitleLabel.setText("ADMINISTRADOR PRECIOS");

        ratesGoBackButton.setText("VOLVER");
        ratesGoBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratesGoBackButtonActionPerformed(evt);
            }
        });

        developerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        developerLabel.setText("CASANAS SOFTWARE");

        updateRate.setText("CAMBIAR PRECIO");
        updateRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRateActionPerformed(evt);
            }
        });

        rateSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        rateChangeStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rateChangeStatus.setText("-");
        rateChangeStatus.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(developerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(rateChangeStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                        .addComponent(updateRate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ratesGoBackButton, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                        .addComponent(rateSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ratesTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ratesTitleLabel)
                .addGap(22, 22, 22)
                .addComponent(rateSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(updateRate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rateChangeStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(ratesGoBackButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(developerLabel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setRateTypeSelector() {
        QueryManagment queryManagment = new QueryManagment();
        ArrayList<Object> ratesData = queryManagment.getRatesName();
        ArrayList<String> ratesName = (ArrayList<String>) ratesData.get(1);

        if (ratesName != null && !ratesName.isEmpty()) {
            for (String rate : ratesName) {
                rateSelector.addItem(rate.toUpperCase());
            }
        } else {
            System.out.println("No se encontraron tarifas para agregar al selector.");
        }
    }

    //Trata cambiar la tarifa y vuelve a imprimir la nueva
    private void updateRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateRateActionPerformed
        Rates rates = new Rates();
        User user = new User();

        double newRate = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nueva tarifa: "));
        String rateToUpdate = rateSelector.getSelectedItem().toString();

        rates.updateRate(this, Integer.parseInt(user.getBusiness_id()), rateToUpdate, newRate);
        ArrayList<Double> ratesData = parking.getRates();
        printRates(ratesData);
    }//GEN-LAST:event_updateRateActionPerformed

    private void ratesGoBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ratesGoBackButtonActionPerformed
        DashboardUI dashboard = new DashboardUI();
        dashboard.setVisible(true);
        dashboard.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_ratesGoBackButtonActionPerformed

    //Obtiene las tarifas desde ratesManagment y asigna esos valores a los JLabel de tarifas
    private void printRates(ArrayList<Double> ratesData) {
        String rates = "Carro: $" + String.valueOf(ratesData.get(0)) + " - Moto: $" + String.valueOf(ratesData.get(1)) + " - Bicicleta: $" + String.valueOf(ratesData.get(2));
        rateChangeStatus.setText(rates);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel developerLabel;
    private javax.swing.JLabel rateChangeStatus;
    private javax.swing.JComboBox<String> rateSelector;
    private javax.swing.JButton ratesGoBackButton;
    private javax.swing.JLabel ratesTitleLabel;
    private javax.swing.JButton updateRate;
    // End of variables declaration//GEN-END:variables
}
