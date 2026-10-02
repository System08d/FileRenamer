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
 * Supported file renaming modes.
 *
 * Each mode contains the translation key used to display
 * its human-readable name in the UI.
 */

public enum RenameMode {
    
    RANDOM("pattern.random"),
    NUMBERING("pattern.numbering"),
    NUMBERING_FROM("pattern.numberingFrom"),
    PREFIX("pattern.prefix"),
    SUFFIX("pattern.suffix"),
    WORD_NUMBER("pattern.wordNumber"),
    REMOVE_SUBSTRING("pattern.removeSubstring"),
    REMOVE_CHARS("pattern.removeChars"),
    DATE("pattern.date"); 

    // Translation key corresponding to this enum value.
    private final String key;

    RenameMode(String key) {
        this.key = key;
    }

/**
 * Returns the translation key associated with this option.
 *
 * @return translation key
 */
    
    public String getKey() {
        return key;
    }
    
}
