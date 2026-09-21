/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

import java.io.File;
import java.security.SecureRandom;

/**
 *
 * @author siste
 */

public class RandomNumberStrategy implements RenameStrategy {

    private final SecureRandom random = new SecureRandom();

    @Override
    public String generateName(File file, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}