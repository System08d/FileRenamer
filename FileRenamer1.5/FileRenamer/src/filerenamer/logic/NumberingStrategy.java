/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

import java.io.File;

/**
 *
 * @author siste
 */

public class NumberingStrategy implements RenameStrategy {

    @Override
    public String generateName(File file, int index) {
        return String.valueOf(index);
    }
}
