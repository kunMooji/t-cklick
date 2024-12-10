package com.panel;

import koneksi.konek;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;


public class konsultasi extends javax.swing.JPanel {
    private Connection connection;
    private JPanel cardPanel;
    private ArrayList<DokterData> dokterList;

    private class DokterData {
        String nama, jamPraktek, noTelp;
        
        public DokterData(String nama, String jamPraktek, String noTelp) {
            this.nama = nama;
            this.jamPraktek = jamPraktek;
            this.noTelp = noTelp;
        }
    }

    public konsultasi() {
        init();
        setupUI();  //nyiapin tampilan UI
        loadDokterData(); //ngambil data dokter dari database
    }

    private void openWhatsApp(String notelp) {
        try {
            String cleanNumber = notelp.replaceAll("[^0-9]", ""); //hapus semua karakter selain angka, buat format nomor yang bersih
            
            if (!cleanNumber.startsWith("62") && cleanNumber.startsWith("0")) {
                cleanNumber = "62" + cleanNumber.substring(1); //ubah nomor telp yang diawali 0 jadi format internasional Indonesia
            }
            
            String url = "https://wa.me/" + cleanNumber; //bikin link WhatsApp
            Desktop.getDesktop().browse(new URI(url)); //nyalain browser dan buka link WhatsApp
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "error membuka whatsapp: " + e.getMessage(),
                "error",
                JOptionPane.ERROR_MESSAGE); //kalo error muncul pop-up pesan
        }
    }

private void setupUI() {
    setLayout(new BorderLayout(10, 10)); // layout utama
    setBackground(new Color(245, 245, 245));

    // panel judul
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(new Color(70, 130, 180));
    titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // jarak lebih kecil
    JLabel titleLabel = new JLabel("Daftar Dokter Tersedia");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22)); // font lebih kecil
    titleLabel.setForeground(Color.WHITE);
    titlePanel.add(titleLabel);

    // panel grid kartu
    cardPanel = new JPanel(new GridLayout(0, 2, 8, 15)) { // jarak antar kartu lebih kecil
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(getParent().getWidth(), super.getPreferredSize().height);
        }
    };
    cardPanel.setBackground(new Color(250, 250, 250));
    cardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // padding lebih kecil

    JScrollPane scrollPane = new JScrollPane(cardPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // border lebih tipis
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    // tambahkan ke frame
    add(titlePanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);

    // koneksi database
    try {
        connection = konek.GetConnection();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error koneksi database: " + e.getMessage());
    }
}


private void loadDokterData() {
    dokterList = new ArrayList<>(); // bikin list kosong buat nampung data dokter
    try {
        String query = "SELECT nama_dokter, jam_praktek, no_telp FROM dokter"; // query buat ambil data dokter
        Statement stmt = connection.createStatement(); // bikin statement buat jalanin query
        ResultSet rs = stmt.executeQuery(query); // jalanin query, hasilnya masuk ke rs

        while (rs.next()) {
            DokterData dokter = new DokterData(
                rs.getString("nama_dokter"), // ambil nama dokter, dst
                rs.getString("jam_praktek"), 
                rs.getString("no_telp") 
            );
            dokterList.add(dokter); // tambahin dokter ke list
            addDokterCard(dokter); // bikin kartu dokter dan tempelin ke tampilan
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage()); // kalau error ambil data, kasih tau
    }
}

    private void openPendaftaranFrame(DokterData dokter) {
        JFrame pendaftaranFrame = new JFrame("Pendaftaran Konsultasi - " + dokter.nama);
        pendaftaranFrame.setSize(440, 600);
        pendaftaranFrame.setLocationRelativeTo(null);

            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    int x = (screenWidth / 2) - (pendaftaranFrame.getWidth() / 2) - 360; // geser 100px ke kiri
    int y = (screenHeight / 2) - (pendaftaranFrame.getHeight() / 2);
    pendaftaranFrame.setLocation(x, y);

    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Dokter Info Section
        JPanel dokterInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        dokterInfoPanel.setBorder(BorderFactory.createTitledBorder("Informasi Dokter"));
        dokterInfoPanel.add(new JLabel("Nama Dokter:"));
        dokterInfoPanel.add(new JLabel(dokter.nama));
        dokterInfoPanel.add(new JLabel("Jam Praktek:"));
        dokterInfoPanel.add(new JLabel(dokter.jamPraktek));
        dokterInfoPanel.add(new JLabel("No. Telepon:"));
        dokterInfoPanel.add(new JLabel(dokter.noTelp));

        // Pendaftaran Form Section
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pendaftaran"));

        JTextField namaField = new JTextField();
        JTextField umurField = new JTextField();
        JTextField nomorTeleponField = new JTextField();
        JTextArea keluhanArea = new JTextArea(4, 20);
        keluhanArea.setLineWrap(true);
        keluhanArea.setWrapStyleWord(true);

        formPanel.add(new JLabel("Nama Pasien:"));
        formPanel.add(namaField);
        formPanel.add(new JLabel("Umur:"));
        formPanel.add(umurField);
        formPanel.add(new JLabel("Nomor Telepon:"));
        formPanel.add(nomorTeleponField);
        formPanel.add(new JLabel("Keluhan:"));
        formPanel.add(new JScrollPane(keluhanArea));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton daftarButton = new JButton("Daftar");
        daftarButton.setBackground(new Color(70, 130, 180));
        daftarButton.setForeground(Color.WHITE);

        daftarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validasi input
                if (namaField.getText().isEmpty() || umurField.getText().isEmpty() || 
                    nomorTeleponField.getText().isEmpty() || keluhanArea.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(pendaftaranFrame, 
                        "Harap lengkapi semua field pendaftaran", 
                        "Validasi", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Simpan pendaftaran ke database (implementasi sesuaikan dengan struktur database Anda)
                try {
                    String query = "INSERT INTO pendaftaran (nama_pasien, umur, nomor_telepon, keluhan, nama_dokter) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, namaField.getText());
                    pstmt.setInt(2, Integer.parseInt(umurField.getText()));
                    pstmt.setString(3, nomorTeleponField.getText());
                    pstmt.setString(4, keluhanArea.getText());
                    pstmt.setString(5, dokter.nama);

                    int result = pstmt.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(pendaftaranFrame, 
                            "Pendaftaran Berhasil!", 
                            "Konfirmasi", 
                            JOptionPane.INFORMATION_MESSAGE);
                        // Opsional: reset form
                        namaField.setText("");
                        umurField.setText("");
                        nomorTeleponField.setText("");
                        keluhanArea.setText("");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(pendaftaranFrame, 
                        "Error pendaftaran: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(daftarButton);

        // Add all panels to main panel
        mainPanel.add(dokterInfoPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        pendaftaranFrame.add(mainPanel);
        pendaftaranFrame.setVisible(true);
    }

   private void addDokterCard(DokterData dokter) {
    JPanel card = new JPanel(new BorderLayout(10, 10)); // layout kartu
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
        BorderFactory.createEmptyBorder(12, 12, 12, 12) // padding lebih kecil
    ));

    // header dengan icon dan nama
    JPanel headerPanel = new JPanel(new BorderLayout(8, 0));
    headerPanel.setOpaque(false);
    JLabel iconLabel = new JLabel("\uD83D\uDC68\u200D⚕️", JLabel.LEFT);
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28)); // icon lebih kecil
    JLabel nameLabel = new JLabel(dokter.nama, JLabel.LEFT);
    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15)); // font nama lebih kecil
    headerPanel.add(iconLabel, BorderLayout.WEST);
    headerPanel.add(nameLabel, BorderLayout.CENTER);

    // panel info tambahan
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setOpaque(false);
    infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0)); // spacing lebih kecil
    infoPanel.add(createInfoLabel("⏰ Jam Praktek: " + dokter.jamPraktek));
    infoPanel.add(Box.createVerticalStrut(4)); // jarak antar info
    infoPanel.add(createInfoLabel("📞 " + dokter.noTelp));

    // tombol aksi
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 8, 0));
    buttonPanel.setOpaque(false);
    JButton contactBtn = createButton("Contact via WhatsApp", new Color(37, 211, 102), e -> openWhatsApp(dokter.noTelp));
    JButton daftarBtn = createButton("Daftar", new Color(40, 167, 69), e -> openPendaftaranFrame(dokter));
    buttonPanel.add(contactBtn);
    buttonPanel.add(daftarBtn);

    // tambah ke kartu
    card.add(headerPanel, BorderLayout.NORTH);
    card.add(infoPanel, BorderLayout.CENTER);
    card.add(buttonPanel, BorderLayout.SOUTH);

    // tambahkan kartu ke panel utama
    cardPanel.add(card);
    cardPanel.revalidate();
    cardPanel.repaint();
}

// metode bantu untuk membuat label info
private JLabel createInfoLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    return label;
}

// metode bantu untuk membuat tombol
private JButton createButton(String text, Color bgColor, ActionListener action) {
    JButton button = new JButton(text);
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setBackground(bgColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // padding tombol
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.addActionListener(action);

    // efek hover
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor.darker());
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor);
        }
    });
    return button;
}



    @SuppressWarnings("unchecked")
    private void init() {
        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1116, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1116, Short.MAX_VALUE)
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 816, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 542, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
