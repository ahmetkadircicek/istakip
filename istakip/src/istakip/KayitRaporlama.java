/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package istakip;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.text.Document;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;



/**
 *
 * @author Ahmet
 */
public class KayitRaporlama extends javax.swing.JFrame {

    /**
     * Creates new form YeniKayıt
     */ 
    public KayitRaporlama() {    
        initComponents();
        setExtendedState(MAXIMIZED_BOTH); 
        
    tblRaporlama.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    for (int column = 0; column < tblRaporlama.getColumnCount(); column++) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) tblRaporlama.getColumnModel();
        TableColumn col = colModel.getColumn(column);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = tblRaporlama.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(tblRaporlama, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < tblRaporlama.getRowCount(); r++) {
            renderer = tblRaporlama.getCellRenderer(r, column);
            comp = tblRaporlama.prepareRenderer(renderer, r, column);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        col.setPreferredWidth(width + 10);
    }

    
    



        
        
        try {
                    Connection conn = null;
                    conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/istakip", "root", "0000");
                    PreparedStatement stmt = conn.prepareStatement("SELECT ikn, isin_adi, isin_cinsi, isin_yapildigi_yer, ada, pafta, parsel, yaklasik_maliyet, butce, butce_yili, ihale_tarihi, ihale_onay_tarihi,"
                    + "sozlesme_bedeli, sozlesme_tarihi, yer_teslim_tarihi, isin_suresi, sure_uzatimi, gecici_kabul_tarihi, kesin_kabul_tarihi, tutanak_tarihi,"
                    + "kontrol_teskilati, muayne_kabul_komisyonu, fiziki_gerceklesme, toplam_odeme" + " FROM proje_bilgileri");
                   
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {

                        String ikn = rs.getString("ikn");
                        String isin_adi = rs.getString("isin_adi");
                        String isin_cinsi = rs.getString("isin_cinsi");
                        String isin_yapildigi_yer = rs.getString("isin_yapildigi_yer");
                        String ada = rs.getString("ada");
                        String pafta = rs.getString("pafta");
                        String parsel = rs.getString("parsel");
                        String yaklasik_maliyet = rs.getString("yaklasik_maliyet");
                        String butce = rs.getString("butce");
                        String butce_yili = rs.getString("butce_yili");
                        String ihale_tarihi = rs.getString("ihale_tarihi");
                        String ihale_onay_tarihi = rs.getString("ihale_onay_tarihi");
                        String sozlesme_bedeli = rs.getString("sozlesme_bedeli");
                        String sozlesme_tarihi = rs.getString("sozlesme_tarihi");
                        String yer_teslim_tarihi = rs.getString("yer_teslim_tarihi");
                        String isin_suresi = rs.getString("isin_suresi");
                        String sure_uzatimi = rs.getString("sure_uzatimi");
                        String gecici_kabul_tarihi = rs.getString("gecici_kabul_tarihi");
                        String kesin_kabul_tarihi = rs.getString("kesin_kabul_tarihi");
                        String tutanak_tarihi = rs.getString("tutanak_tarihi");
                        String kontrol_teskilati = rs.getString("kontrol_teskilati");
                        String muayne_kabul_komisyonu = rs.getString("muayne_kabul_komisyonu");
                        String fiziki_gerceklesme = rs.getString("fiziki_gerceklesme");
                        String toplam_odeme = rs.getString("toplam_odeme");
                        
                        String tblData[] = {ikn, isin_adi, isin_cinsi,isin_yapildigi_yer, ada, pafta, parsel,yaklasik_maliyet, butce, butce_yili, ihale_tarihi,ihale_onay_tarihi, sozlesme_bedeli, sozlesme_tarihi,yer_teslim_tarihi, isin_suresi, sure_uzatimi, gecici_kabul_tarihi,kesin_kabul_tarihi, tutanak_tarihi, kontrol_teskilati, muayne_kabul_komisyonu,  fiziki_gerceklesme, toplam_odeme};
                        
                         DefaultTableModel tblModel = (DefaultTableModel)tblRaporlama.getModel();
                         
                         tblModel.addRow(tblData);
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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
        pnlLeft = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRaporlama = new javax.swing.JTable();
        btnGeriDon = new javax.swing.JButton();
        lblYeniKayit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 900));

        pnlBackground.setBackground(new java.awt.Color(255, 255, 255));

        pnlLeft.setBackground(new java.awt.Color(51, 51, 51));
        pnlLeft.setForeground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        tblRaporlama.setAutoCreateRowSorter(true);
        tblRaporlama.setBackground(new java.awt.Color(255, 255, 255));
        tblRaporlama.setForeground(new java.awt.Color(0, 0, 0));
        tblRaporlama.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ikn", "isin_adi", "isin_cinsi", "isin_yapildigi_yer", "ada", "pafta", "parsel", "yaklasik_maliyet", "butce", "butce_yili", "ihale_tarihi", "ihale_onay_tarihi", "sozlesme_bedeli", "sozlesme_tarihi", "yer_teslim_tarihi", "isin_suresi", "sure_uzatimi", "gecici_kabul_tarihi", "kesin_kabul_tarihi", "tutanak_tarihi", "kontrot_sekilati", "muayne_kabul_komisyonu", "fiziki_gerceklesme", "toplam_odeme"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRaporlama.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblRaporlama.setGridColor(new java.awt.Color(51, 51, 51));
        tblRaporlama.setRowHeight(30);
        tblRaporlama.setRowMargin(10);
        tblRaporlama.setRowSelectionAllowed(false);
        tblRaporlama.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblRaporlama.setShowGrid(true);
        jScrollPane1.setViewportView(tblRaporlama);

        btnGeriDon.setBackground(new java.awt.Color(255, 255, 255));
        btnGeriDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGeriDon.setForeground(new java.awt.Color(51, 51, 51));
        btnGeriDon.setText("Geri Dön");
        btnGeriDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeriDonActionPerformed(evt);
            }
        });

        lblYeniKayit.setBackground(new java.awt.Color(51, 51, 51));
        lblYeniKayit.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblYeniKayit.setForeground(new java.awt.Color(255, 255, 255));
        lblYeniKayit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblYeniKayit.setText("Kayıt Raporlama");

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblYeniKayit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(115, 115, 115))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLeftLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGeriDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1616, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblYeniKayit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGeriDon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnGeriDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeriDonActionPerformed
        new AnaMenu().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnGeriDonActionPerformed
    
    
    
    
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
            java.util.logging.Logger.getLogger(KayitRaporlama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KayitRaporlama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KayitRaporlama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KayitRaporlama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new KayitRaporlama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGeriDon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblYeniKayit;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JTable tblRaporlama;
    // End of variables declaration//GEN-END:variables
}
