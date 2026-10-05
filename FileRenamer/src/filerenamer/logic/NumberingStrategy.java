/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

/**
 *
 * @author siste
 */

import java.io.File;

/**
 * Generates sequential numeric names based on the file index.
 */

public class NumberingStrategy implements RenameStrategy {

    @Override
    public String generateName(File file, int index) {
        return String.valueOf(index);
    }
}
