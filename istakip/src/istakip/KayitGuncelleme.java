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



/**
 *
 * @author Ahmet
 */
public class KayitGuncelleme extends javax.swing.JFrame {

    /**
     * Creates new form YeniKayıt
     */ 
    public KayitGuncelleme() {    
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);  
        
        dateSozlesmeTarihi.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String days_string = txpIsinSuresi.getText();
                if(days_string.isEmpty() || !days_string.matches("\\d+")){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number of days!");
                    return;
                }
                int days = Integer.parseInt(days_string);
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateSozlesmeTarihi.getDate());
                cal.add(Calendar.DATE, days);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                txpBitisTarihi.setText(sdf.format(cal.getTime()));
            }
        }); 
      
        txpIsinSuresi.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            if (dateSozlesmeTarihi.getDate() != null) {
            calculate();
            }
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void calculate() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateSozlesmeTarihi.getDate());
                int days = Integer.parseInt(txpIsinSuresi.getText());
                cal.add(Calendar.DATE, days);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                txpBitisTarihi.setText(sdf.format(cal.getTime()));
            }           
        });
        
        txpSureUzatimi.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            if (dateSozlesmeTarihi.getDate() != null && txpBitisTarihi.getText() != null) {
            calculate();
            }
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void calculate() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateSozlesmeTarihi.getDate());
                int days = Integer.parseInt(txpIsinSuresi.getText()) + Integer.parseInt(txpSureUzatimi.getText());
                cal.add(Calendar.DATE, days);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                txpUzatimliIsBitisTarihi.setText(sdf.format(cal.getTime()));
            }       
        });     
                 
        txpSozlesmeBedeli.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculate();
            }

            private void calculate() {
                String sozlesmeBedeliText = txpSozlesmeBedeli.getText();
                String yaklasikMaliyetText = txpYaklasikMaliyet.getText();
                if (!sozlesmeBedeliText.isEmpty() && !yaklasikMaliyetText.isEmpty()) {
                    double sozlesmeBedeli = Double.parseDouble(sozlesmeBedeliText);
                    double yaklasikMaliyet = Double.parseDouble(yaklasikMaliyetText);
                    double tenzilatOrani = sozlesmeBedeli / yaklasikMaliyet;
                    txpTenzilatOrani.setText(String.valueOf(tenzilatOrani));
                }
            }
        });
        
        txpYaklasikMaliyet.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculate();
            }

            private void calculate() {
                String sozlesmeBedeliText = txpYaklasikMaliyet.getText();
                String yaklasikMaliyetText = txpSozlesmeBedeli.getText();
                if (!sozlesmeBedeliText.isEmpty() && !yaklasikMaliyetText.isEmpty()) {
                    double sozlesmeBedeli = Double.parseDouble(sozlesmeBedeliText);
                    double yaklasikMaliyet = Double.parseDouble(yaklasikMaliyetText);
                    double tenzilatOrani = sozlesmeBedeli / yaklasikMaliyet;
                    txpTenzilatOrani.setText(String.valueOf(tenzilatOrani));
                }
            }
        });
        
        try {
            Connection conn = null;
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/istakip", "root", "0000");
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT ikn FROM proje_bilgileri");
            while (rs.next()) {
                String ikn = rs.getString("ikn");
                cmbIkn.addItem(ikn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();   
        }

        cmbIkn.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    Connection conn = null;
                    conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/istakip", "root", "0000");

                    PreparedStatement pstmt = conn.prepareStatement("SELECT ikn, isin_adi, isin_cinsi, isin_yapildigi_yer, ada, pafta, parsel, yaklasik_maliyet, butce, butce_yili, ihale_tarihi, ihale_onay_tarihi,"
                    + "sozlesme_bedeli, sozlesme_tarihi, yer_teslim_tarihi, isin_suresi, sure_uzatimi, gecici_kabul_tarihi, kesin_kabul_tarihi, tutanak_tarihi,"
                    + "kontrol_teskilati, muayne_kabul_komisyonu, fiziki_gerceklesme, toplam_odeme" + " FROM proje_bilgileri WHERE ikn = ?");
                    
                    pstmt.setString(1, cmbIkn.getSelectedItem().toString());
                    ResultSet rs = pstmt.executeQuery();
                    while(rs.next()) {
                        txpIsinAdi.setText(rs.getString("isin_adi"));
                        txpIsinAdi.setText(rs.getString("isin_adi"));
                        cmbIsinCinsi.setSelectedItem(rs.getString("isin_cinsi"));
                        cmbIsinYapildigiYer.setSelectedItem(rs.getString("isin_yapildigi_yer"));
                        txpAda.setText(rs.getString("ada"));
                        txpPafta.setText(rs.getString("pafta"));
                        txpParsel.setText(rs.getString("parsel"));
                        txpYaklasikMaliyet.setText(rs.getString("yaklasik_maliyet"));
                        txpButce.setText(rs.getString("butce"));
                        cmbButceYili.setSelectedItem(rs.getString("butce_yili"));
                        dateIhaleTarihi.setDate(rs.getDate("ihale_tarihi"));
                        dateIhaleOnayTarihi.setDate(rs.getDate("ihale_onay_tarihi"));
                        txpSozlesmeBedeli.setText(rs.getString("sozlesme_bedeli"));
                        dateSozlesmeTarihi.setDate(rs.getDate("sozlesme_tarihi"));
                        dateYerTeslimTarihi.setDate(rs.getDate("yer_teslim_tarihi"));
                        txpIsinSuresi.setText(rs.getString("isin_suresi"));
                        txpSureUzatimi.setText(rs.getString("sure_uzatimi"));
                        dateGeciciKabulTarihi.setDate(rs.getDate("gecici_kabul_tarihi"));
                        dateKesinKabulTarihi.setDate(rs.getDate("kesin_kabul_tarihi"));
                        dateTutanakTarihi.setDate(rs.getDate("tutanak_tarihi"));
                        txpKontrolTeskilati.setText(rs.getString("kontrol_teskilati"));
                        txpMuayneKabulKomisyonu.setText(rs.getString("muayne_kabul_komisyonu"));
                        txpFizikiGerceklesme.setText(rs.getString("fiziki_gerceklesme"));
                        txpToplamOdeme.setText(rs.getString("toplam_odeme"));  
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }    
            }
                      
        });
        
        cmbIkn.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(cmbHakEdisler.getItemCount() > 0)
                    cmbHakEdisler.removeAllItems();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istakip", "root", "0000");
                     PreparedStatement pstmt = conn.prepareStatement("SELECT ikn, hak_edis_tarihi, hak_edis_bedeli FROM hak_edis_bilgileri WHERE ikn = ?")) {
                    pstmt.setString(1, cmbIkn.getSelectedItem().toString());
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            java.sql.Date hakedistarihi = rs.getDate("hak_edis_tarihi");
                            String hakedisbedeli = rs.getString("hak_edis_bedeli");
                            String item = hakedisbedeli + "₺" + " - " + dateFormat.format(hakedistarihi);
                            cmbHakEdisler.addItem(item);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        
        
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
        pnlRight = new javax.swing.JPanel();
        lblIKN = new javax.swing.JLabel();
        lblIsinAdi = new javax.swing.JLabel();
        txpIsinAdi = new javax.swing.JTextPane();
        lblIsinYapildigiYer = new javax.swing.JLabel();
        txpAda = new javax.swing.JTextPane();
        lblAda = new javax.swing.JLabel();
        lblIsinCinsi = new javax.swing.JLabel();
        txpYaklasikMaliyet = new javax.swing.JTextPane();
        lblYaklasikMaliyet = new javax.swing.JLabel();
        txpButce = new javax.swing.JTextPane();
        lblButce = new javax.swing.JLabel();
        lblIhaleTarihi = new javax.swing.JLabel();
        lblIhaleOnayTarihi = new javax.swing.JLabel();
        btnGeriDon = new javax.swing.JButton();
        proje_numarasi30 = new javax.swing.JLabel();
        cmbIsinYapildigiYer = new javax.swing.JComboBox<>();
        txpPafta = new javax.swing.JTextPane();
        lblPafta = new javax.swing.JLabel();
        dateIhaleTarihi = new com.toedter.calendar.JDateChooser();
        dateIhaleOnayTarihi = new com.toedter.calendar.JDateChooser();
        txpParsel = new javax.swing.JTextPane();
        lblParsel = new javax.swing.JLabel();
        cmbIsinCinsi = new javax.swing.JComboBox<>();
        cmbButceYili = new javax.swing.JComboBox<>();
        cmbIkn = new javax.swing.JComboBox<>();
        pnlMiddle = new javax.swing.JPanel();
        lblHakEdisBedeli = new javax.swing.JLabel();
        txpHakEdisBedeli = new javax.swing.JTextPane();
        lblHakEdisTarihi = new javax.swing.JLabel();
        lblGeciciKabulTarihi = new javax.swing.JLabel();
        lblKesinKabulTarihi = new javax.swing.JLabel();
        lblTutanakTarihi = new javax.swing.JLabel();
        lblKontrolTeskilati = new javax.swing.JLabel();
        txpMuayneKabulKomisyonu = new javax.swing.JTextPane();
        lblMuayneKabulKomisyonu = new javax.swing.JLabel();
        txpFizikiGerceklesme = new javax.swing.JTextPane();
        lblFizikiGerceklesme = new javax.swing.JLabel();
        txpToplamOdeme = new javax.swing.JTextPane();
        lblToplamOdeme = new javax.swing.JLabel();
        btnTemizle = new javax.swing.JButton();
        proje_numarasi32 = new javax.swing.JLabel();
        txpKontrolTeskilati = new javax.swing.JTextPane();
        btnEkle = new javax.swing.JButton();
        dateHakEdisTarihi = new com.toedter.calendar.JDateChooser();
        dateGeciciKabulTarihi = new com.toedter.calendar.JDateChooser();
        dateKesinKabulTarihi = new com.toedter.calendar.JDateChooser();
        dateTutanakTarihi = new com.toedter.calendar.JDateChooser();
        btnFotograf = new javax.swing.JButton();
        cmbHakEdisler = new javax.swing.JComboBox<>();
        lblHakEdisler = new javax.swing.JLabel();
        pnlLeft = new javax.swing.JPanel();
        txpSozlesmeBedeli = new javax.swing.JTextPane();
        lblSozlesmeBedeli = new javax.swing.JLabel();
        lblSozlesmeTarihi = new javax.swing.JLabel();
        lblYerTeslimTarihi = new javax.swing.JLabel();
        txpIsinSuresi = new javax.swing.JTextPane();
        lblIsinSuresi = new javax.swing.JLabel();
        lblSureUzatimi = new javax.swing.JLabel();
        lblUzatimliIsBitisTarihi = new javax.swing.JLabel();
        lblYeniKayit = new javax.swing.JLabel();
        btnKaydet = new javax.swing.JButton();
        proje_numarasi31 = new javax.swing.JLabel();
        lblBitisTarihi = new javax.swing.JLabel();
        txpSureUzatimi = new javax.swing.JTextPane();
        txpTenzilatOrani = new javax.swing.JTextPane();
        lblTenzilatOrani = new javax.swing.JLabel();
        dateSozlesmeTarihi = new com.toedter.calendar.JDateChooser();
        dateYerTeslimTarihi = new com.toedter.calendar.JDateChooser();
        txpUzatimliIsBitisTarihi = new javax.swing.JTextPane();
        txpBitisTarihi = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 900));

        pnlBackground.setBackground(new java.awt.Color(255, 255, 255));

        pnlRight.setBackground(new java.awt.Color(51, 51, 51));
        pnlRight.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlRight.setForeground(new java.awt.Color(255, 255, 255));

        lblIKN.setBackground(new java.awt.Color(51, 51, 51));
        lblIKN.setForeground(new java.awt.Color(255, 255, 255));
        lblIKN.setText("İ.K.N [ Zorunlu ]");

        lblIsinAdi.setBackground(new java.awt.Color(51, 51, 51));
        lblIsinAdi.setForeground(new java.awt.Color(255, 255, 255));
        lblIsinAdi.setText("İşin Adı");

        txpIsinAdi.setBackground(new java.awt.Color(255, 255, 255));
        txpIsinAdi.setForeground(new java.awt.Color(0, 0, 0));

        lblIsinYapildigiYer.setBackground(new java.awt.Color(51, 51, 51));
        lblIsinYapildigiYer.setForeground(new java.awt.Color(255, 255, 255));
        lblIsinYapildigiYer.setText("İşin Yapıldığı Yer");

        txpAda.setBackground(new java.awt.Color(255, 255, 255));
        txpAda.setForeground(new java.awt.Color(0, 0, 0));

        lblAda.setBackground(new java.awt.Color(51, 51, 51));
        lblAda.setForeground(new java.awt.Color(255, 255, 255));
        lblAda.setText("Ada");

        lblIsinCinsi.setBackground(new java.awt.Color(51, 51, 51));
        lblIsinCinsi.setForeground(new java.awt.Color(255, 255, 255));
        lblIsinCinsi.setText("İşin Cinsi");

        txpYaklasikMaliyet.setBackground(new java.awt.Color(255, 255, 255));
        txpYaklasikMaliyet.setForeground(new java.awt.Color(0, 0, 0));
        txpYaklasikMaliyet.setToolTipText("");
        txpYaklasikMaliyet.setAlignmentX(5.0F);

        lblYaklasikMaliyet.setBackground(new java.awt.Color(51, 51, 51));
        lblYaklasikMaliyet.setForeground(new java.awt.Color(255, 255, 255));
        lblYaklasikMaliyet.setText("Yaklaşık Maliyet");

        txpButce.setBackground(new java.awt.Color(255, 255, 255));
        txpButce.setForeground(new java.awt.Color(0, 0, 0));

        lblButce.setBackground(new java.awt.Color(51, 51, 51));
        lblButce.setForeground(new java.awt.Color(255, 255, 255));
        lblButce.setText("Bütçe");

        lblIhaleTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblIhaleTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblIhaleTarihi.setText("İhale Tarihi");

        lblIhaleOnayTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblIhaleOnayTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblIhaleOnayTarihi.setText("İhale Onay Tarihi");

        btnGeriDon.setBackground(new java.awt.Color(255, 255, 255));
        btnGeriDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGeriDon.setForeground(new java.awt.Color(51, 51, 51));
        btnGeriDon.setText("Geri Dön");
        btnGeriDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeriDonActionPerformed(evt);
            }
        });

        proje_numarasi30.setBackground(new java.awt.Color(51, 51, 51));
        proje_numarasi30.setForeground(new java.awt.Color(255, 255, 255));

        cmbIsinYapildigiYer.setBackground(new java.awt.Color(255, 255, 255));
        cmbIsinYapildigiYer.setForeground(new java.awt.Color(255, 255, 255));
        cmbIsinYapildigiYer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Adalar", "Arnavutköy", "Ataşehir", "Avcılar", "Bağcılar", "Bahçelievler", "Bakırköy", "Başakşehir", "Bayrampaşa", "Beşiktaş", "Beykoz", "Beylikdüzü", "Beyoğlu", "Büyükçekmece", "Çatalca", "Çekmeköy", "Esenler", "Esenyurt", "Eyüpsultan", "Fatih", "Gaziosmanpaşa", "Güngören", "Kadıköy", "Kağıthane", "Kartal", "Küçükçekmece", "Maltepe", "Pendik", "Sancaktepe", "Sarıyer", "Silivri", "Sultanbeyli", "Sultangazi", "Şile", "Şişli", "Tuzla", "Ümraniye", "Üsküdar", "Zeytinburnu" }));
        cmbIsinYapildigiYer.setSelectedIndex(-1);

        txpPafta.setBackground(new java.awt.Color(255, 255, 255));
        txpPafta.setForeground(new java.awt.Color(0, 0, 0));

        lblPafta.setBackground(new java.awt.Color(51, 51, 51));
        lblPafta.setForeground(new java.awt.Color(255, 255, 255));
        lblPafta.setText("Pafta");

        dateIhaleTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateIhaleTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateIhaleTarihi.setDateFormatString("dd.MM.y");

        dateIhaleOnayTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateIhaleOnayTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateIhaleOnayTarihi.setDateFormatString("dd.MM.y");

        txpParsel.setBackground(new java.awt.Color(255, 255, 255));
        txpParsel.setForeground(new java.awt.Color(0, 0, 0));

        lblParsel.setBackground(new java.awt.Color(51, 51, 51));
        lblParsel.setForeground(new java.awt.Color(255, 255, 255));
        lblParsel.setText("Parsel");

        cmbIsinCinsi.setBackground(new java.awt.Color(255, 255, 255));
        cmbIsinCinsi.setForeground(new java.awt.Color(51, 51, 51));
        cmbIsinCinsi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "19. Madde Açık İhale", "20. Madde Belli İstekliler", "21. Pazarlık", "21/a. Teklif Çıkmaması", "21/b. Doğal Afet", "21/c. Savunma ve Güvenlik", "21/d. Araştırma Geliştirme", "21/e. Karmaşık İşler\t", "21//f. Limitli Alım", "22. Doğrudan Temin", "22/a. Tek Yetkili", "22/b. Özel Hak", "22/c. Asıl Sözleşmeye Dayalı Alım", "22/d. Limitli Alım", "22/e. Taşınmaz Mal Alımı", "22/f. Sağlık Sarf Malzemeleri", "Protoko" }));
        cmbIsinCinsi.setSelectedIndex(-1);
        cmbIsinCinsi.setToolTipText("");

        cmbButceYili.setBackground(new java.awt.Color(255, 255, 255));
        cmbButceYili.setForeground(new java.awt.Color(0, 0, 0));
        cmbButceYili.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050" }));
        cmbButceYili.setSelectedIndex(-1);

        cmbIkn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        cmbIkn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIknActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRightLayout = new javax.swing.GroupLayout(pnlRight);
        pnlRight.setLayout(pnlRightLayout);
        pnlRightLayout.setHorizontalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIhaleOnayTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIhaleTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblButce, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblYaklasikMaliyet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIsinYapildigiYer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGeriDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proje_numarasi30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateIhaleOnayTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateIhaleTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlRightLayout.createSequentialGroup()
                        .addComponent(txpButce, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(cmbButceYili, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblIsinCinsi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIsinAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIKN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txpIsinAdi)
                    .addComponent(txpYaklasikMaliyet)
                    .addComponent(cmbIsinYapildigiYer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRightLayout.createSequentialGroup()
                        .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txpAda)
                            .addComponent(lblAda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txpPafta)
                            .addComponent(lblPafta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txpParsel)
                            .addComponent(lblParsel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(cmbIsinCinsi, javax.swing.GroupLayout.Alignment.TRAILING, 0, 402, Short.MAX_VALUE)
                    .addComponent(cmbIkn, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        pnlRightLayout.setVerticalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblIKN, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(cmbIkn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(lblIsinAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpIsinAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(lblIsinCinsi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(cmbIsinCinsi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(lblIsinYapildigiYer, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(cmbIsinYapildigiYer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRightLayout.createSequentialGroup()
                        .addComponent(lblAda, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txpAda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRightLayout.createSequentialGroup()
                        .addComponent(lblParsel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txpParsel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRightLayout.createSequentialGroup()
                        .addComponent(lblPafta, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txpPafta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(lblYaklasikMaliyet, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpYaklasikMaliyet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 29, Short.MAX_VALUE)
                .addComponent(lblButce, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txpButce, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(cmbButceYili))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(lblIhaleTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dateIhaleTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 28, Short.MAX_VALUE)
                .addComponent(lblIhaleOnayTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dateIhaleOnayTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(proje_numarasi30, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnGeriDon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pnlMiddle.setBackground(new java.awt.Color(51, 51, 51));
        pnlMiddle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlMiddle.setForeground(new java.awt.Color(255, 255, 255));

        lblHakEdisBedeli.setBackground(new java.awt.Color(51, 51, 51));
        lblHakEdisBedeli.setForeground(new java.awt.Color(255, 255, 255));
        lblHakEdisBedeli.setText("Hak Ediş Bedeli");

        txpHakEdisBedeli.setBackground(new java.awt.Color(255, 255, 255));
        txpHakEdisBedeli.setForeground(new java.awt.Color(0, 0, 0));

        lblHakEdisTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblHakEdisTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblHakEdisTarihi.setText("Hak Ediş Tarihi");

        lblGeciciKabulTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblGeciciKabulTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblGeciciKabulTarihi.setText("Geçeci Kabul Tarihi");

        lblKesinKabulTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblKesinKabulTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblKesinKabulTarihi.setText("Kesin Kabul Tarihi");

        lblTutanakTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblTutanakTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblTutanakTarihi.setText("Tutanak Tarihi");

        lblKontrolTeskilati.setBackground(new java.awt.Color(51, 51, 51));
        lblKontrolTeskilati.setForeground(new java.awt.Color(255, 255, 255));
        lblKontrolTeskilati.setText("Kontrol Teşkilatı");

        txpMuayneKabulKomisyonu.setBackground(new java.awt.Color(255, 255, 255));
        txpMuayneKabulKomisyonu.setForeground(new java.awt.Color(0, 0, 0));

        lblMuayneKabulKomisyonu.setBackground(new java.awt.Color(51, 51, 51));
        lblMuayneKabulKomisyonu.setForeground(new java.awt.Color(255, 255, 255));
        lblMuayneKabulKomisyonu.setText("Muayne Kabul Komisyonu");

        txpFizikiGerceklesme.setBackground(new java.awt.Color(255, 255, 255));
        txpFizikiGerceklesme.setForeground(new java.awt.Color(0, 0, 0));

        lblFizikiGerceklesme.setBackground(new java.awt.Color(51, 51, 51));
        lblFizikiGerceklesme.setForeground(new java.awt.Color(255, 255, 255));
        lblFizikiGerceklesme.setText("Fiziki Gerçekleşme (%)");

        txpToplamOdeme.setBackground(new java.awt.Color(255, 255, 255));
        txpToplamOdeme.setForeground(new java.awt.Color(0, 0, 0));

        lblToplamOdeme.setBackground(new java.awt.Color(51, 51, 51));
        lblToplamOdeme.setForeground(new java.awt.Color(255, 255, 255));
        lblToplamOdeme.setText("Toplam Ödeme");

        btnTemizle.setBackground(new java.awt.Color(255, 255, 255));
        btnTemizle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTemizle.setForeground(new java.awt.Color(51, 51, 51));
        btnTemizle.setText("Temizle");
        btnTemizle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemizleActionPerformed(evt);
            }
        });

        proje_numarasi32.setBackground(new java.awt.Color(51, 51, 51));
        proje_numarasi32.setForeground(new java.awt.Color(255, 255, 255));

        txpKontrolTeskilati.setBackground(new java.awt.Color(255, 255, 255));
        txpKontrolTeskilati.setForeground(new java.awt.Color(0, 0, 0));

        btnEkle.setBackground(new java.awt.Color(255, 255, 255));
        btnEkle.setForeground(new java.awt.Color(51, 51, 51));
        btnEkle.setText("Ekle");
        btnEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEkleActionPerformed(evt);
            }
        });

        dateHakEdisTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateHakEdisTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateHakEdisTarihi.setDateFormatString("dd.MM.y");

        dateGeciciKabulTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateGeciciKabulTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateGeciciKabulTarihi.setDateFormatString("dd.MM.y");

        dateKesinKabulTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateKesinKabulTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateKesinKabulTarihi.setDateFormatString("dd.MM.y");

        dateTutanakTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateTutanakTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateTutanakTarihi.setDateFormatString("dd.MM.y");

        btnFotograf.setBackground(new java.awt.Color(255, 255, 255));
        btnFotograf.setForeground(new java.awt.Color(0, 0, 0));
        btnFotograf.setText("Fotoğraf");
        btnFotograf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFotografActionPerformed(evt);
            }
        });

        lblHakEdisler.setBackground(new java.awt.Color(51, 51, 51));
        lblHakEdisler.setForeground(new java.awt.Color(255, 255, 255));
        lblHakEdisler.setText("Hak Edisler");

        javax.swing.GroupLayout pnlMiddleLayout = new javax.swing.GroupLayout(pnlMiddle);
        pnlMiddle.setLayout(pnlMiddleLayout);
        pnlMiddleLayout.setHorizontalGroup(
            pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMiddleLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(proje_numarasi32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHakEdisBedeli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMiddleLayout.createSequentialGroup()
                        .addComponent(txpHakEdisBedeli, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(btnEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblGeciciKabulTarihi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKesinKabulTarihi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKontrolTeskilati, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTutanakTarihi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMuayneKabulKomisyonu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFizikiGerceklesme, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblToplamOdeme, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateKesinKabulTarihi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateGeciciKabulTarihi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateTutanakTarihi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txpKontrolTeskilati, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTemizle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMiddleLayout.createSequentialGroup()
                        .addComponent(txpFizikiGerceklesme)
                        .addGap(20, 20, 20)
                        .addComponent(btnFotograf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txpToplamOdeme, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpMuayneKabulKomisyonu, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMiddleLayout.createSequentialGroup()
                        .addGroup(pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbHakEdisler, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHakEdisler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblHakEdisTarihi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateHakEdisTarihi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        pnlMiddleLayout.setVerticalGroup(
            pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMiddleLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblHakEdisBedeli, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txpHakEdisBedeli, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnEkle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHakEdisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHakEdisler, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateHakEdisTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(cmbHakEdisler))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lblGeciciKabulTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dateGeciciKabulTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(lblKesinKabulTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dateKesinKabulTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(lblTutanakTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dateTutanakTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 35, Short.MAX_VALUE)
                .addComponent(lblKontrolTeskilati, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpKontrolTeskilati, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(lblMuayneKabulKomisyonu, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpMuayneKabulKomisyonu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(lblFizikiGerceklesme, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpFizikiGerceklesme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFotograf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 36, Short.MAX_VALUE)
                .addComponent(lblToplamOdeme, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpToplamOdeme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(proje_numarasi32, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnTemizle, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        btnEkle.getAccessibleContext().setAccessibleName("btnEkle");

        pnlLeft.setBackground(new java.awt.Color(51, 51, 51));
        pnlLeft.setForeground(new java.awt.Color(255, 255, 255));

        txpSozlesmeBedeli.setBackground(new java.awt.Color(255, 255, 255));
        txpSozlesmeBedeli.setForeground(new java.awt.Color(0, 0, 0));

        lblSozlesmeBedeli.setBackground(new java.awt.Color(51, 51, 51));
        lblSozlesmeBedeli.setForeground(new java.awt.Color(255, 255, 255));
        lblSozlesmeBedeli.setText("Sözleşme Bedeli");

        lblSozlesmeTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblSozlesmeTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblSozlesmeTarihi.setText("Sözleşme Tarihi");

        lblYerTeslimTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblYerTeslimTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblYerTeslimTarihi.setText("Yer Teslim Tarihi");

        txpIsinSuresi.setBackground(new java.awt.Color(255, 255, 255));
        txpIsinSuresi.setForeground(new java.awt.Color(0, 0, 0));

        lblIsinSuresi.setBackground(new java.awt.Color(51, 51, 51));
        lblIsinSuresi.setForeground(new java.awt.Color(255, 255, 255));
        lblIsinSuresi.setText("İşin Süresi");

        lblSureUzatimi.setBackground(new java.awt.Color(51, 51, 51));
        lblSureUzatimi.setForeground(new java.awt.Color(255, 255, 255));
        lblSureUzatimi.setText("Süre Uzatımı");

        lblUzatimliIsBitisTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblUzatimliIsBitisTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblUzatimliIsBitisTarihi.setText("Uzatımlı İş Bitiş Tarihi");

        lblYeniKayit.setBackground(new java.awt.Color(51, 51, 51));
        lblYeniKayit.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblYeniKayit.setForeground(new java.awt.Color(255, 255, 255));
        lblYeniKayit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblYeniKayit.setText("Kayıt Güncelleme");

        btnKaydet.setBackground(new java.awt.Color(255, 255, 255));
        btnKaydet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKaydet.setForeground(new java.awt.Color(51, 51, 51));
        btnKaydet.setText("Kaydet");
        btnKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKaydetActionPerformed(evt);
            }
        });

        proje_numarasi31.setBackground(new java.awt.Color(51, 51, 51));
        proje_numarasi31.setForeground(new java.awt.Color(255, 255, 255));

        lblBitisTarihi.setBackground(new java.awt.Color(51, 51, 51));
        lblBitisTarihi.setForeground(new java.awt.Color(255, 255, 255));
        lblBitisTarihi.setText("Bitiş Tarihi");

        txpSureUzatimi.setBackground(new java.awt.Color(255, 255, 255));
        txpSureUzatimi.setForeground(new java.awt.Color(0, 0, 0));

        txpTenzilatOrani.setEditable(false);
        txpTenzilatOrani.setBackground(new java.awt.Color(255, 255, 255));
        txpTenzilatOrani.setForeground(new java.awt.Color(0, 0, 0));

        lblTenzilatOrani.setBackground(new java.awt.Color(51, 51, 51));
        lblTenzilatOrani.setForeground(new java.awt.Color(255, 255, 255));
        lblTenzilatOrani.setText("Tenzilat Oranı ");

        dateSozlesmeTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateSozlesmeTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateSozlesmeTarihi.setDateFormatString("dd.MM.y");

        dateYerTeslimTarihi.setBackground(new java.awt.Color(255, 255, 255));
        dateYerTeslimTarihi.setForeground(new java.awt.Color(255, 255, 255));
        dateYerTeslimTarihi.setDateFormatString("dd.MM.y");

        txpUzatimliIsBitisTarihi.setEditable(false);
        txpUzatimliIsBitisTarihi.setBackground(new java.awt.Color(255, 255, 255));
        txpUzatimliIsBitisTarihi.setForeground(new java.awt.Color(0, 0, 0));

        txpBitisTarihi.setEditable(false);
        txpBitisTarihi.setBackground(new java.awt.Color(255, 255, 255));
        txpBitisTarihi.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUzatimliIsBitisTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSureUzatimi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIsinSuresi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblYerTeslimTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSozlesmeTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSozlesmeBedeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proje_numarasi31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenzilatOrani, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(txpSureUzatimi)
                    .addComponent(txpIsinSuresi)
                    .addComponent(txpSozlesmeBedeli)
                    .addComponent(lblYeniKayit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKaydet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBitisTarihi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateSozlesmeTarihi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateYerTeslimTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txpTenzilatOrani)
                    .addComponent(txpUzatimliIsBitisTarihi)
                    .addComponent(txpBitisTarihi))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblYeniKayit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(lblSozlesmeBedeli, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpSozlesmeBedeli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(lblTenzilatOrani, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpTenzilatOrani, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 26, Short.MAX_VALUE)
                .addComponent(lblYerTeslimTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dateYerTeslimTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 30, Short.MAX_VALUE)
                .addComponent(lblSozlesmeTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dateSozlesmeTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 26, Short.MAX_VALUE)
                .addComponent(lblIsinSuresi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpIsinSuresi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
                .addComponent(lblBitisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txpBitisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(lblSureUzatimi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txpSureUzatimi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(lblUzatimliIsBitisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txpUzatimliIsBitisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(proje_numarasi31, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnKaydet, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(pnlLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(pnlMiddle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMiddle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLeft, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    
    private void btnTemizleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemizleActionPerformed
        new KayitGuncelleme().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnTemizleActionPerformed

    private void btnGeriDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeriDonActionPerformed
        new AnaMenu().setVisible(true);
        dispose();   
    }//GEN-LAST:event_btnGeriDonActionPerformed

    private void btnKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKaydetActionPerformed
            
    }//GEN-LAST:event_btnKaydetActionPerformed

    private void btnEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEkleActionPerformed
        
        if (txpHakEdisBedeli.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lütfen hak ediş bedelini giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
        else if(dateHakEdisTarihi.getDate()==null) {
                JOptionPane.showMessageDialog(null, "Lütfen hak ediş tarihini giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
        }
        else{
            Connection conn = null;
    
        try {
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/istakip", "root", "0000");
        } 
        catch (SQLException ex) {
                ex.printStackTrace();
        }

            String sqlHakEdis = "INSERT INTO hak_edis_bilgileri (ikn, hak_edis_bedeli, hak_edis_tarihi)" +
                     "VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlHakEdis)) 
        {          
            
            if (cmbIkn.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lütfen bir ihale kontrol numarası giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                pstmt.setInt(1, Integer.parseInt(cmbIkn.getSelectedItem().toString()        ));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Girilen ihale kontrol numarası geçersiz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            pstmt.setInt(2, txpHakEdisBedeli.getText().isEmpty() ? 0 : Integer.parseInt(txpHakEdisBedeli.getText()));
            pstmt.setDate(3, new java.sql.Date(dateHakEdisTarihi.getDate().getTime()));
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Başarıyla Eklendi!");

            } 
        catch (SQLException ex) {
            
            ex.printStackTrace();
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = dateFormat.format(dateHakEdisTarihi.getDate());
        String bedelString = txpHakEdisBedeli.getText();
        String combinedString = bedelString + "₺" + " - " + dateString;
        cmbHakEdisler.addItem(combinedString);
        }
        
        
    }//GEN-LAST:event_btnEkleActionPerformed

    private void btnFotografActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotografActionPerformed
        if (cmbIkn.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lütfen bir ihale kontrol numarası giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        String documentsPath = System.getProperty("user.home") + "/Documents";
        File newDir = new File(documentsPath + "/Proje Detayları");
        if (!newDir.exists()) {
            newDir.mkdir();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Fotoğraf Seçin");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                String folderName = cmbIkn.getSelectedItem().toString(); // insert here the textpane where you get the folder name
                File newFolder = new File(newDir.getAbsolutePath() + "/" + folderName);
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                try {
                    File outputFile = new File(newFolder.getAbsolutePath() + "/" + selectedFile.getName());
                    ImageIO.write(image, "jpg", outputFile);
                    JOptionPane.showMessageDialog(null, "Fotoğraf başarıyla kaydedildi!", "Başarı", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnFotografActionPerformed

    private void cmbIknActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIknActionPerformed

    }//GEN-LAST:event_cmbIknActionPerformed

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
            java.util.logging.Logger.getLogger(KayitGuncelleme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KayitGuncelleme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KayitGuncelleme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KayitGuncelleme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KayitGuncelleme().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEkle;
    private javax.swing.JButton btnFotograf;
    private javax.swing.JButton btnGeriDon;
    private javax.swing.JButton btnKaydet;
    private javax.swing.JButton btnTemizle;
    private javax.swing.JComboBox<String> cmbButceYili;
    private javax.swing.JComboBox<String> cmbHakEdisler;
    private javax.swing.JComboBox<String> cmbIkn;
    private javax.swing.JComboBox<String> cmbIsinCinsi;
    private javax.swing.JComboBox<String> cmbIsinYapildigiYer;
    private com.toedter.calendar.JDateChooser dateGeciciKabulTarihi;
    private com.toedter.calendar.JDateChooser dateHakEdisTarihi;
    private com.toedter.calendar.JDateChooser dateIhaleOnayTarihi;
    private com.toedter.calendar.JDateChooser dateIhaleTarihi;
    private com.toedter.calendar.JDateChooser dateKesinKabulTarihi;
    private com.toedter.calendar.JDateChooser dateSozlesmeTarihi;
    private com.toedter.calendar.JDateChooser dateTutanakTarihi;
    private com.toedter.calendar.JDateChooser dateYerTeslimTarihi;
    private javax.swing.JLabel lblAda;
    private javax.swing.JLabel lblBitisTarihi;
    private javax.swing.JLabel lblButce;
    private javax.swing.JLabel lblFizikiGerceklesme;
    private javax.swing.JLabel lblGeciciKabulTarihi;
    private javax.swing.JLabel lblHakEdisBedeli;
    private javax.swing.JLabel lblHakEdisTarihi;
    private javax.swing.JLabel lblHakEdisler;
    private javax.swing.JLabel lblIKN;
    private javax.swing.JLabel lblIhaleOnayTarihi;
    private javax.swing.JLabel lblIhaleTarihi;
    private javax.swing.JLabel lblIsinAdi;
    private javax.swing.JLabel lblIsinCinsi;
    private javax.swing.JLabel lblIsinSuresi;
    private javax.swing.JLabel lblIsinYapildigiYer;
    private javax.swing.JLabel lblKesinKabulTarihi;
    private javax.swing.JLabel lblKontrolTeskilati;
    private javax.swing.JLabel lblMuayneKabulKomisyonu;
    private javax.swing.JLabel lblPafta;
    private javax.swing.JLabel lblParsel;
    private javax.swing.JLabel lblSozlesmeBedeli;
    private javax.swing.JLabel lblSozlesmeTarihi;
    private javax.swing.JLabel lblSureUzatimi;
    private javax.swing.JLabel lblTenzilatOrani;
    private javax.swing.JLabel lblToplamOdeme;
    private javax.swing.JLabel lblTutanakTarihi;
    private javax.swing.JLabel lblUzatimliIsBitisTarihi;
    private javax.swing.JLabel lblYaklasikMaliyet;
    private javax.swing.JLabel lblYeniKayit;
    private javax.swing.JLabel lblYerTeslimTarihi;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlMiddle;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JLabel proje_numarasi30;
    private javax.swing.JLabel proje_numarasi31;
    private javax.swing.JLabel proje_numarasi32;
    private javax.swing.JTextPane txpAda;
    private javax.swing.JTextPane txpBitisTarihi;
    private javax.swing.JTextPane txpButce;
    private javax.swing.JTextPane txpFizikiGerceklesme;
    private javax.swing.JTextPane txpHakEdisBedeli;
    private javax.swing.JTextPane txpIsinAdi;
    private javax.swing.JTextPane txpIsinSuresi;
    private javax.swing.JTextPane txpKontrolTeskilati;
    private javax.swing.JTextPane txpMuayneKabulKomisyonu;
    private javax.swing.JTextPane txpPafta;
    private javax.swing.JTextPane txpParsel;
    private javax.swing.JTextPane txpSozlesmeBedeli;
    private javax.swing.JTextPane txpSureUzatimi;
    private javax.swing.JTextPane txpTenzilatOrani;
    private javax.swing.JTextPane txpToplamOdeme;
    private javax.swing.JTextPane txpUzatimliIsBitisTarihi;
    private javax.swing.JTextPane txpYaklasikMaliyet;
    // End of variables declaration//GEN-END:variables
}
