/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package filerenamer;

import filerenamer.ui.MainFrame;

/**
 *
 * @author siste
 */

public class Main {
    
/**
 * Application entry point.
 *
 * Initializes the Swing look and feel and starts the main application
 * window on the AWT event dispatch thread.
 */
    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}