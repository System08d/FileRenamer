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
import java.security.SecureRandom;

/**
 * Generates a random 12-digit numeric name for each file.
 *
 * The generated value is independent of the original file name
 * and file index.
 */

public class RandomNumberStrategy implements RenameStrategy {
    
               // SecureRandom is used to generate the digits of the new name.
    private final SecureRandom random = new SecureRandom();
    private static final int NAME_LENGTH = 12;
    
    @Override
    public String generateName(File file, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NAME_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}