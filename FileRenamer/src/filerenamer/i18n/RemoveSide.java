/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

/**
 *
 * @author oleksandr.dan
 */

package filerenamer.i18n;

/**
 * Defines the side from which characters or content are removed.
 *
 * Each value is associated with the translation key used to display
 * the option in the user interface.
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
