/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author oleksandr.dan
 */


public final class I18n {
    
    private static Locale currentLocale = Locale.forLanguageTag("ru");

    private static ResourceBundle bundle =
            ResourceBundle.getBundle(
                    "filerenamer.i18n.messages",
                    currentLocale
            );

    private I18n() {
    }

    public static void setLanguage(String language) {
        currentLocale = Locale.forLanguageTag(language);

        bundle = ResourceBundle.getBundle(
                "filerenamer.i18n.messages",
                currentLocale
        );
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static String get(String key, Object... arguments) {
        return MessageFormat.format(
                bundle.getString(key),
                arguments
        );
    }

    public static String getLanguage() {
        return currentLocale.getLanguage();
    }
}
    
