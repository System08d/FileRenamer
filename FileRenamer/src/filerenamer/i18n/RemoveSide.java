/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package filerenamer.i18n;

/**
 *
 * @author oleksandr.dan
 */
public enum RemoveSide {
    
      START("side.start"),
    END("side.end");

    private final String key;

    RemoveSide(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    
}
