package com.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

public class ButtonX extends JButton {

    public ButtonX() {
        setPreferredSize(new Dimension(40, 40)); // Ukuran tombol
        setFocusPainted(false);                 // Hilangkan fokus default
        setContentAreaFilled(false);           // Hilangkan background default
        setBorderPainted(false);               // Hilangkan border default
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Aktifkan anti-aliasing untuk menggambar yang lebih halus
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gambar lingkaran sebagai latar belakang
        g2.setColor(new Color(255, 83, 83)); // Warna merah
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Gambar tanda "X"
        g2.setColor(Color.WHITE);           // Warna putih untuk "X"
        g2.setStroke(new java.awt.BasicStroke(3)); // Ketebalan garis
        int margin = 10;                   // Margin dalam dari lingkaran
        g2.drawLine(margin, margin, getWidth() - margin, getHeight() - margin); // Garis pertama
        g2.drawLine(getWidth() - margin, margin, margin, getHeight() - margin); // Garis kedua

        g2.dispose();
    }
}
