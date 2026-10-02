/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.i18n;

/**
 *
 * @author oleksandr.dan
 */

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Central access point for application translations.
 *
 * The class keeps the currently selected locale and the corresponding
 * resource bundle. All user-facing localized messages should be retrieved
 * through this class instead of accessing {@link ResourceBundle} directly.
 *
 * Translation keys are resolved from the
 * {@code filerenamer.i18n.messages} resource bundle.
 */

public final class I18n {
    
    // Default application language.
    private static Locale currentLocale = Locale.forLanguageTag("ru");

    private static ResourceBundle bundle =
            ResourceBundle.getBundle(
                    "filerenamer.i18n.messages",
                    currentLocale
            );

    private I18n() {
    }
    
/**
 * Changes the application's current language and reloads the
 * corresponding translation bundle.
 *
 * @param language language tag accepted by {@link Locale#forLanguageTag(String)}
 */

    public static void setLanguage(String language) {
        currentLocale = Locale.forLanguageTag(language);

        bundle = ResourceBundle.getBundle(
                "filerenamer.i18n.messages",
                currentLocale
        );
    }
    
/**
 * Returns the localized message associated with the given translation key.
 *
 * @param key translation key from the resource bundle
 * @return localized message
 */

    public static String get(String key) {
        return bundle.getString(key);
    }
    
/**
 * Returns a localized message and replaces its
 * {@link MessageFormat} placeholders with the supplied arguments.
 *
 * @param key translation key from the resource bundle
 * @param arguments values used to format the message
 * @return formatted localized message
 */

    public static String get(String key, Object... arguments) {
        return MessageFormat.format(
                bundle.getString(key),
                arguments
        );
    }
    
/**
 * Returns the language code of the currently selected locale.
 *
 * @return current language code, for example {@code "ru"}
 */

    public static String getLanguage() {
        return currentLocale.getLanguage();
    }
}
    
