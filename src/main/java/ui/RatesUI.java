/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import controller.BusinessModel;
import javax.swing.JOptionPane;
import network.Rates;
import controller.User;
import utilities.IOOperations;
import java.util.ArrayList;
import network.BusinessItems;
import network.Checkout;

/**
 *
 * @author Felipe
 */
public class RatesUI extends javax.swing.JFrame {

    public RatesUI() {
        initComponents();

        setRateTypeSelector();
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
        updateRate = new javax.swing.JButton();
        rateSelector = new javax.swing.JComboBox<>();
        rateChangeStatus = new javax.swing.JLabel();
        queryRatesButton = new javax.swing.JButton();
        branding = new javax.swing.JPanel();
        goBackButton = new javax.swing.JButton();
        developerLabel = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("rates"); // NOI18N
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ratesTitleLabel.setFont(new java.awt.Font("Gill Sans MT", 2, 24)); // NOI18N
        ratesTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        ratesTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ratesTitleLabel.setText("ADMINISTRADOR PRECIOS");
        getContentPane().add(ratesTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, -1, -1, 70));

        updateRate.setBackground(new java.awt.Color(0, 153, 51));
        updateRate.setFont(new java.awt.Font("Gill Sans MT Condensed", 2, 18)); // NOI18N
        updateRate.setForeground(new java.awt.Color(255, 255, 255));
        updateRate.setText("CAMBIAR PRECIO");
        updateRate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRateActionPerformed(evt);
            }
        });
        getContentPane().add(updateRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 261, -1));

        rateSelector.setFont(new java.awt.Font("Gill Sans MT Condensed", 2, 18)); // NOI18N
        rateSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        rateSelector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(rateSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 261, -1));

        rateChangeStatus.setFont(new java.awt.Font("Gill Sans MT Condensed", 2, 24)); // NOI18N
        rateChangeStatus.setForeground(new java.awt.Color(255, 255, 255));
        rateChangeStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rateChangeStatus.setText("-");
        rateChangeStatus.setToolTipText("");
        getContentPane().add(rateChangeStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 400, 40));

        queryRatesButton.setBackground(new java.awt.Color(0, 102, 153));
        queryRatesButton.setFont(new java.awt.Font("Gill Sans MT Condensed", 2, 18)); // NOI18N
        queryRatesButton.setForeground(new java.awt.Color(255, 255, 255));
        queryRatesButton.setText("CONSULTAR");
        queryRatesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        queryRatesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryRatesButtonActionPerformed(evt);
            }
        });
        getContentPane().add(queryRatesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 261, -1));

        branding.setBackground(new java.awt.Color(255, 255, 255));
        branding.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        goBackButton.setBackground(new java.awt.Color(204, 0, 0));
        goBackButton.setForeground(new java.awt.Color(255, 255, 255));
        goBackButton.setText("<-");
        goBackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        goBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackButtonActionPerformed(evt);
            }
        });
        branding.add(goBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 70, 30));

        developerLabel.setFont(new java.awt.Font("Gill Sans MT Condensed", 2, 18)); // NOI18N
        developerLabel.setForeground(new java.awt.Color(0, 0, 0));
        developerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        developerLabel.setText("CASANAS SOFTWARE");
        branding.add(developerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 40));

        getContentPane().add(branding, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 420, 40));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background.jpeg"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 280));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setRateTypeSelector() {
        ArrayList<Object> ratesData = BusinessItems.getRatesName(User.getBusiness_id());
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

        double newRate = Double.parseDouble(IOOperations.sanitizeInput(JOptionPane.showInputDialog("Ingrese la nueva tarifa: ")));
        String rateToUpdate = rateSelector.getSelectedItem().toString();

        printRates(rates.updateRate(this, Integer.parseInt(user.getBusiness_id()), rateToUpdate, newRate));
    }//GEN-LAST:event_updateRateActionPerformed

    private void queryRatesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryRatesButtonActionPerformed
        // Obtener los datos de las tarifas
        ArrayList<Object> ratesList = new Rates().getRates(Integer.parseInt(new User().getBusiness_id()));
        ArrayList<String> rateName = (ArrayList<String>) ratesList.get(1);
        ArrayList<Double> rate = (ArrayList<Double>) ratesList.get(2);

        // Crear un StringBuilder para construir el mensaje
        StringBuilder message = new StringBuilder("Tarifas actuales:\n");

        // Validar que ambas listas tienen el mismo tamaño antes de iterar
        if (rateName.size() == rate.size()) {
            for (int i = 0; i < rateName.size(); i++) {
                message.append((i + 1) + ". ").append(rateName.get(i).toUpperCase()).append(": $").append(rate.get(i)).append("\n");
            }
        } else {
            message.append("Error: Las listas de nombres y valores no coinciden en tamaño.");
        }

        // Mostrar el mensaje en un JOptionPane
        JOptionPane.showMessageDialog(null, message.toString(), "Tarifas", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_queryRatesButtonActionPerformed

    private void goBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackButtonActionPerformed
        DashboardUI dashboard = new DashboardUI();
        dashboard.setVisible(true);
        dashboard.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_goBackButtonActionPerformed

    //Obtiene las tarifas desde ratesManagment y asigna esos valores a los JLabel de tarifas
    private void printRates(boolean updated) {
        if (updated) {
            rateChangeStatus.setText("La tarifa fue actualizada");
        } else {
            rateChangeStatus.setText("Error al actualizar la tarifa");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JPanel branding;
    private javax.swing.JLabel developerLabel;
    private javax.swing.JButton goBackButton;
    private javax.swing.JButton queryRatesButton;
    private javax.swing.JLabel rateChangeStatus;
    private javax.swing.JComboBox<String> rateSelector;
    private javax.swing.JLabel ratesTitleLabel;
    private javax.swing.JButton updateRate;
    // End of variables declaration//GEN-END:variables
}
