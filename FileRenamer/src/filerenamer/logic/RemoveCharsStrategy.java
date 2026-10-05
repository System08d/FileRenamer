/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

/**
 *
 * @author siste
 * 
 */

import java.io.File;

/**
 * Generates names by removing a specified number of characters
 * from the beginning or end of the original base name.
 *
 * If the number of characters to remove is greater than or equal
 * to the base name length, a fallback name in the form
 * {@code file_<index>} is generated.
 */

public class RemoveCharsStrategy implements RenameStrategy {

/**
 * Defines the side from which characters are removed.
 */

    public enum Side {
        START, END
    }

    private final int count;
    private final Side side;
    
/**
 * Creates a strategy that removes the specified number of characters
 * from the selected side of the file name.
 *
 * @param count number of characters to remove
 * @param side side from which characters are removed
 */
    
    public RemoveCharsStrategy(int count, Side side) {
        this.count = count;
        this.side = side;
    }

    @Override
    public String generateName(File file, int index) {
        String originalName = file.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String baseName = (dotIndex > 0) ? originalName.substring(0, dotIndex) : originalName;

        if (count >= baseName.length()) {
            return "file_" + index;
        }

        if (side == Side.START) {
            return baseName.substring(count);
        } else {
            return baseName.substring(0, baseName.length() - count);
        }
    }
}