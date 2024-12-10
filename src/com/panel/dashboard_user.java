package com.panel;

import com.main.Login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class dashboard_user extends javax.swing.JPanel {

    private int userId; // menyimpan id pengguna

    public dashboard_user(int userId) {
        this.userId = userId; // menyimpan ID pengguna
        initComponents();
        init();
    }

    private void init() {
        setLayout(new BorderLayout(10, 10));  
        setBackground(new Color(245, 245, 245));  

        // panel judul
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));  
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  

        JLabel titleLabel = new JLabel("Data Pengguna");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));  
        titleLabel.setForeground(Color.WHITE);  
        titlePanel.add(titleLabel);  

        // panel data pengguna
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dataPanel.setBackground(Color.WHITE); 

        // menampilkan data pengguna
        JTextField userIdLabel = new JTextField("ID: ");
        JTextField usernameLabel = new JTextField("Username: ");
        JTextField emailLabel = new JTextField("Email: ");
        JTextField phoneLabel = new JTextField("No. Telepon: ");
        JTextField fullNameLabel = new JTextField("Fullname: ");
        JTextField dobLabel = new JTextField("Tanggal Lahir: ");

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 18);
        userIdLabel.setFont(labelFont);
        usernameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        phoneLabel.setFont(labelFont);
        fullNameLabel.setFont(labelFont);
        dobLabel.setFont(labelFont);

        dataPanel.add(userIdLabel);
        dataPanel.add(Box.createVerticalStrut(10));
        dataPanel.add(usernameLabel);
        dataPanel.add(Box.createVerticalStrut(10));
        dataPanel.add(emailLabel);
        dataPanel.add(Box.createVerticalStrut(10));
        dataPanel.add(phoneLabel);
        dataPanel.add(Box.createVerticalStrut(10));
        dataPanel.add(fullNameLabel);
        dataPanel.add(Box.createVerticalStrut(10));
        dataPanel.add(dobLabel);

        // button Logout
        JButton logout = new JButton("Logout");
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logout.setBackground(new Color(255, 102, 102));  
        logout.setForeground(Color.WHITE);  
        logout.addActionListener(e -> LogoutAction());

        dataPanel.add(Box.createVerticalStrut(20));
        dataPanel.add(logout);

        // tambahkan komponen utama ke layout
        add(titlePanel, BorderLayout.NORTH);  
        add(dataPanel, BorderLayout.CENTER);  

        loadUserData(userIdLabel, usernameLabel, emailLabel, phoneLabel, fullNameLabel, dobLabel);
    }

    private void LogoutAction() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin logout?", "", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            ((javax.swing.JFrame) this.getTopLevelAncestor()).dispose(); // nutup frame
        }
    }

    private void loadUserData(JTextField userIdLabel, JTextField usernameLabel, JTextField emailLabel, JTextField phoneLabel, JTextField fullNameLabel, JTextField dobLabel) {
        try (Connection conn = koneksi.konek.GetConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, username, email, no_telepon, fullname, tanggal_lahir FROM akun WHERE id = ?")) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userIdLabel.setText("ID: " + rs.getInt("id"));
                    usernameLabel.setText("Username: " + rs.getString("username"));
                    emailLabel.setText("Email: " + rs.getString("email"));
                    phoneLabel.setText("No. Telepon: " + rs.getString("no_telepon"));
                    fullNameLabel.setText("Fullname: " + rs.getString("fullname"));
                    dobLabel.setText("Tanggal Lahir: " + rs.getDate("tanggal_lahir"));
                } else {
                    JOptionPane.showMessageDialog(this, "Data pengguna tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data pengguna: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
