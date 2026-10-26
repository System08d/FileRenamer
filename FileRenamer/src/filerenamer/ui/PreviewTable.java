/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.ui;

/**
 *
 * @author oleksandr.dan
 */

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JTable;

/** Таблица, которая в пустом состоянии рисует серую подсказку по центру. */
public class PreviewTable extends JTable {

    private String hint = "";

    public PreviewTable() {
        setFillsViewportHeight(true);   // таблица занимает всю высоту области, даже без строк
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        // сначала обычная отрисовка таблицы
        if (getRowCount() == 0 && !hint.isEmpty()) {
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.GRAY);
            g.drawString(hint, (getWidth() - fm.stringWidth(hint)) / 2, getHeight() / 2);
        }
    }
}