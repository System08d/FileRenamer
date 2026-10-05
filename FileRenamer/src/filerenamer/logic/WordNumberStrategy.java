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
 * Generates names by combining a fixed word with the file index.
 */

public class WordNumberStrategy implements RenameStrategy {

    private final String word;

/**
 * Creates a strategy using the specified word as a prefix.
 *
 * @param word word used in generated names
 */
    
    public WordNumberStrategy(String word) {
        this.word = word;
    }

    @Override
    public String generateName(File file, int index) {
        return word + index;
    }
}