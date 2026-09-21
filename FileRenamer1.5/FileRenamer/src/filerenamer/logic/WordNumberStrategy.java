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

public class WordNumberStrategy implements RenameStrategy {

    private final String word;

    public WordNumberStrategy(String word) {
        this.word = word;
    }

    @Override
    public String generateName(File file, int index) {
        return word + index;
    }
}