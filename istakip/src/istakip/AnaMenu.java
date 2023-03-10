/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package istakip;

import javax.swing.JOptionPane;

/**
 *
 * @author Ahmet
 */
public class AnaMenu extends javax.swing.JFrame {

    /**
     * Creates new form home
     */
    public AnaMenu() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        pnlMain = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblIsTakip = new javax.swing.JLabel();
        btnKayitGuncelleme = new javax.swing.JButton();
        btnYeniKayit = new javax.swing.JButton();
        btnKayitSorgulama = new javax.swing.JButton();
        btnCıkısYap = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 900));

        pnlBackground.setBackground(new java.awt.Color(255, 255, 255));

        pnlMain.setBackground(new java.awt.Color(51, 51, 51));
        pnlMain.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.darkGray, java.awt.Color.gray));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        lblIsTakip.setFont(new java.awt.Font("Helvetica Neue", 3, 48)); // NOI18N
        lblIsTakip.setForeground(new java.awt.Color(255, 255, 255));
        lblIsTakip.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIsTakip.setText("İŞ TAKİP");

        btnKayitGuncelleme.setBackground(new java.awt.Color(255, 255, 255));
        btnKayitGuncelleme.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKayitGuncelleme.setForeground(new java.awt.Color(51, 51, 51));
        btnKayitGuncelleme.setText("Kayıt Güncelleme");
        btnKayitGuncelleme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKayitGuncellemeActionPerformed(evt);
            }
        });

        btnYeniKayit.setBackground(new java.awt.Color(255, 255, 255));
        btnYeniKayit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnYeniKayit.setForeground(new java.awt.Color(51, 51, 51));
        btnYeniKayit.setText("Yeni Kayıt");
        btnYeniKayit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYeniKayitActionPerformed(evt);
            }
        });

        btnKayitSorgulama.setBackground(new java.awt.Color(255, 255, 255));
        btnKayitSorgulama.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKayitSorgulama.setForeground(new java.awt.Color(51, 51, 51));
        btnKayitSorgulama.setText("Kayıt Sorgulama");
        btnKayitSorgulama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKayitSorgulamaActionPerformed(evt);
            }
        });

        btnCıkısYap.setBackground(new java.awt.Color(255, 255, 255));
        btnCıkısYap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCıkısYap.setForeground(new java.awt.Color(51, 51, 51));
        btnCıkısYap.setText("Çıkış Yap");
        btnCıkısYap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCıkısYapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap(423, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblIsTakip, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKayitGuncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKayitSorgulama, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCıkısYap, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnYeniKayit, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(423, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap(302, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(lblIsTakip, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(btnYeniKayit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnKayitGuncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnKayitSorgulama, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnCıkısYap, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(333, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKayitGuncellemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKayitGuncellemeActionPerformed
        new KayitGuncelleme().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKayitGuncellemeActionPerformed

    private void btnYeniKayitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYeniKayitActionPerformed
        new YeniKayit().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnYeniKayitActionPerformed

    private void btnKayitSorgulamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKayitSorgulamaActionPerformed
        new KayitRaporlama().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKayitSorgulamaActionPerformed

    private void btnCıkısYapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCıkısYapActionPerformed
        dispose();
    }//GEN-LAST:event_btnCıkısYapActionPerformed

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
            java.util.logging.Logger.getLogger(AnaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnaMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCıkısYap;
    private javax.swing.JButton btnKayitGuncelleme;
    private javax.swing.JButton btnKayitSorgulama;
    private javax.swing.JButton btnYeniKayit;
    private javax.swing.JLabel lblIsTakip;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
