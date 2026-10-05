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
 * Generates names by removing all occurrences of a specified substring
 * from the original base name.
 */

public class RemoveSubstringStrategy implements RenameStrategy {

    private final String toRemove;
    
/**
 * Creates a strategy that removes the specified substring.
 *
 * @param toRemove substring to remove from the file name
 */

    public RemoveSubstringStrategy(String toRemove) {
        this.toRemove = toRemove;
    }

    @Override
    public String generateName(File file, int index) {
        String originalName = file.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String baseName = (dotIndex > 0) ? originalName.substring(0, dotIndex) : originalName;
        return baseName.replace(toRemove, "");
    }
}