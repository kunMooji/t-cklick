package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import koneksi.konek;

public class Login extends javax.swing.JFrame {

     private int userId;
     
    public Login() {
        initComponents();
        close11.event(this);
        setLocationRelativeTo(null);
    }
  public int getUserId() {
        return userId; // Kembalikan ID pengguna
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.swing.background();
        jLabel4 = new javax.swing.JLabel();
        close11 = new com.button.Closelogin();
        login_container = new javax.swing.JPanel();
        password_txt = new javax.swing.JPasswordField();
        username_txt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        registrasi_btn = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        button_login = new com.button.Fbutton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        background1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/psycare_logo.png"))); // NOI18N
        background1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 470, 390));
        background1.add(close11, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, -1, -1));

        login_container.setBackground(new java.awt.Color(255, 255, 255));
        login_container.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        password_txt.setBackground(new java.awt.Color(239, 247, 248));
        password_txt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        login_container.add(password_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 260, 30));

        username_txt.setBackground(new java.awt.Color(239, 247, 248));
        username_txt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        login_container.add(username_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 260, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("Login");
        login_container.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jLabel2.setText("password");
        login_container.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        registrasi_btn.setForeground(new java.awt.Color(51, 102, 255));
        registrasi_btn.setText("registrasi");
        registrasi_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrasi_btnMouseClicked(evt);
            }
        });
        login_container.add(registrasi_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, -1, -1));

        jLabel5.setText("belum punya akun? ");
        login_container.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        jLabel6.setText("username");
        login_container.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        button_login.setText("login");
        button_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_loginActionPerformed(evt);
            }
        });
        login_container.add(button_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 110, 30));

        background1.add(login_container, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 63, 331, 373));

        getContentPane().add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registrasi_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrasi_btnMouseClicked
    registrasi registrasiFrame = new registrasi();
    this.dispose(); //nutup frame this(yg sedang muncul saat ini)
    registrasiFrame.setVisible(true); //terus di ganti sama registrasiFrame
    }//GEN-LAST:event_registrasi_btnMouseClicked

    private void button_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_loginActionPerformed
    String username = username_txt.getText();
    String password = new String(password_txt.getPassword());
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = konek.GetConnection();
        //query nyari role berdasarkan username dan password
        String sql = "SELECT role, id FROM akun WHERE username = ? AND password = ?";
      
      
        //stmt itu statement, conn itu connection
        //rs itu result
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        rs = stmt.executeQuery();
        if (rs.next()) {
            String role = rs.getString("role");
            
            
            //misal role udh ketemu dengan kita masukin usn dan pw nya 
            //dikasih if else , misal role admin masuk ke frame admin, user jg gt
            if ("admin".equals(role)) {
            new admin().setVisible(true);
            this.dispose();
        } else if ("user".equals(role)) {
            int userId = rs.getInt("id"); // Get id from query result
            user usr = new user(userId); // Pass id to user constructor
            usr.setVisible(true);
            this.dispose();
        }

            //misal usn/pw nya salah muncul exception ky gini
        } else {
            JOptionPane.showMessageDialog(this, 
                "Username atau Password salah", 
                "Login Gagal", 
                JOptionPane.ERROR_MESSAGE);
        }
        //catch exception. ini tuh format dari try and catch kalo ga ad ini eror
        //ini buat ngasih tau misal ada eror dari database
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error: " + e.getMessage(), 
            "kesalan dalam database", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }//GEN-LAST:event_button_loginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.background background1;
    private com.button.Fbutton button_login;
    private com.button.Closelogin close11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel login_container;
    private javax.swing.JPasswordField password_txt;
    private javax.swing.JLabel registrasi_btn;
    private javax.swing.JTextField username_txt;
    // End of variables declaration//GEN-END:variables
}
