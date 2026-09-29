/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package filerenamer.i18n;

/**
 *
 * @author oleksandr.dan
 */

public enum RenameMode {
    
    RANDOM("pattern.random"),
    NUMBERING("pattern.numbering"),
    NUMBERING_FROM("pattern.numberingFrom"),
    PREFIX("pattern.prefix"),
    SUFFIX("pattern.suffix"),
    WORD_NUMBER("pattern.wordNumber"),
    REMOVE_SUBSTRING("pattern.removeSubstring"),
    REMOVE_CHARS("pattern.removeChars");

    private final String key;

    RenameMode(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    
}
