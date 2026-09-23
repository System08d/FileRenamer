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

public class RemoveSubstringStrategy implements RenameStrategy {

    private final String toRemove;

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