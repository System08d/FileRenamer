/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package filerenamer.logic;

/**
 *
 * @author siste
 */

import java.io.File;

/**
 * Defines a strategy for generating a new file name.
 *
 * Implementations encapsulate different renaming rules while exposing
 * the same interface to the rest of the application.
 */

public interface RenameStrategy {
    
/**
 * Generates a new name for the specified file.
 *
 * @param file file being renamed
 * @param index one-based position of the file in the current operation
 * @return generated name without the file extension
 */
    
     String generateName(File file, int index);
    
}
