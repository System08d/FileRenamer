/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

import java.io.File;

/**
 *
 * @author oleksandr.dan
 */

public class NumberingFromStrategy implements RenameStrategy {

    private final int startNumber;

    public NumberingFromStrategy(int startNumber) {
        this.startNumber = startNumber;
    }

    @Override
    public String generateName(File file, int index) {
        int number = startNumber + (index - 1);
        return String.valueOf(number);
    }
}