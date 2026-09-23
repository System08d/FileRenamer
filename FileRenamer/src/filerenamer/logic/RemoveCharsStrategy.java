/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

import java.io.File;

/**
 *
 * @author siste
 * 
 */

public class RemoveCharsStrategy implements RenameStrategy {

    public enum Side {
        START, END
    }

    private final int count;
    private final Side side;

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