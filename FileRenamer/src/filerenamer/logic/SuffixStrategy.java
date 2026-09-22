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

public class SuffixStrategy implements RenameStrategy {

    private final String suffix;

    public SuffixStrategy(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String generateName(File file, int index) {
        String originalName = file.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String baseName = (dotIndex > 0) ? originalName.substring(0, dotIndex) : originalName;
        return baseName + suffix;
    }
}