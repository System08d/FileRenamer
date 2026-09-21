/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package filerenamer.logic;

import java.io.File;

/**
 *
 * @author siste
 */
public interface RenameStrategy {
    
     String generateName(File file, int index);
    
}
