package com.button;

import com.swing.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import com.swing.background;
import javax.swing.Action;

public class Tampilan extends javax.swing.JPanel {


    public Tampilan() {
        initComponents();
        setOpaque(false);
    }
    public void event(JFrame frame, background panel){
        close.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent close){
                System.exit(0);
            }
        });
//        restore.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent restore){
//                if(frame.getExtendedState()==JFrame.MAXIMIZED_BOTH){
//                    panel.setRound(20);
//                    frame.setExtendedState(JFrame.NORMAL);
//                }
//                else{
//                    panel.setRound(0);
//                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//                }
//            }
//        });
        mini.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent minimize){
                frame.setState(JFrame.ICONIFIED);
            }
        });
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mini = new com.button.button();
        close = new com.button.button();

        mini.setBackground(new java.awt.Color(255, 255, 0));

        close.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(mini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(close, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(close, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.button.button close;
    private com.button.button mini;
    // End of variables declaration//GEN-END:variables
}
