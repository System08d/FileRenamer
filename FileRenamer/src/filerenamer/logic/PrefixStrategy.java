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
 * Generates names by adding a prefix to the original file name.
 *
 * The file extension is excluded from the generated name.
 * For example, {@code "photo.jpg"} with the prefix {@code "new_"}
 * produces {@code "new_photo"}.
 */

public class PrefixStrategy implements RenameStrategy {

    private final String prefix;

 /**
 * Creates a prefix strategy.
 *
 * @param prefix text added before the original base name
 */
    
    public PrefixStrategy(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String generateName(File file, int index) {
        String originalName = file.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String baseName = (dotIndex > 0) ? originalName.substring(0, dotIndex) : originalName;
        return prefix + baseName;
    }
}