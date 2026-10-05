/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

/**
 *
 * @author oleksandr.dan
 */

import java.io.File;

/**
 * Generates sequential numeric names starting from a specified number.
 *
 * The first file receives {@code startNumber}; each subsequent file
 * receives the next number in the sequence.
 */

public class NumberingFromStrategy implements RenameStrategy {

    private final int startNumber;

/**
 * Creates a numbering strategy with the specified starting number.
 *
 * @param startNumber number assigned to the first file
 */
    
    public NumberingFromStrategy(int startNumber) {
        this.startNumber = startNumber;
    }

    @Override
    public String generateName(File file, int index) {
        int number = startNumber + (index - 1);
        return String.valueOf(number);
    }
}