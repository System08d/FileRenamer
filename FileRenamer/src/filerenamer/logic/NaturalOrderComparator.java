/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.logic;

/**
 *
 * @author siste
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Comparator that sorts strings using natural ordering.
 *
 * Strings are split into consecutive digit, letter and other-character
 * tokens. Digit tokens are compared by their numeric value, so values such
 * as {@code "file2"} and {@code "file10"} are ordered numerically rather
 * than lexicographically.
 *
 * If two numeric tokens have the same numeric value, their original
 * textual representation is used as a tie-breaker.
 */

public class NaturalOrderComparator implements Comparator<String> {

    // Token categories are ordered deliberately:
    // digits < letters < other characters.
    private static final int CATEGORY_DIGIT = 0;
    private static final int CATEGORY_LETTER = 1;
    private static final int CATEGORY_OTHER = 2;

    @Override
    public int compare(String name1, String name2) {
        List<String> tokens1 = splitIntoTokens(name1);
        List<String> tokens2 = splitIntoTokens(name2);

        int minSize = Math.min(tokens1.size(), tokens2.size());

        for (int i = 0; i < minSize; i++) {
            int result = compareTokens(tokens1.get(i), tokens2.get(i));
            if (result != 0) {
                return result;
            }
        }

        return Integer.compare(tokens1.size(), tokens2.size());
    }

/**
 * Splits a string into consecutive tokens of the same character category.
 *
 * For example, {@code "file123-test"} becomes:
 * {@code ["file", "123", "-", "test"]}.
 *
 * Code points are used instead of {@code char} values so that
 * supplementary Unicode characters are processed correctly.
 */
    
    private List<String> splitIntoTokens(String name) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        Integer currentCategory = null;

        int i = 0;
        while (i < name.length()) {
            int codePoint = name.codePointAt(i);
            int category = categoryOf(codePoint);

            if (currentCategory != null && category != currentCategory) {
                tokens.add(currentToken.toString());
                currentToken = new StringBuilder();
            }

            currentToken.appendCodePoint(codePoint);
            currentCategory = category;
            i += Character.charCount(codePoint);
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    // Categories are used to split tokens and determine comparison priority.
    private int categoryOf(int codePoint) {
        if (Character.isDigit(codePoint)) {
            return CATEGORY_DIGIT;
        } else if (Character.isLetter(codePoint)) {
            return CATEGORY_LETTER;
        } else {
            return CATEGORY_OTHER;
        }
    }

/**
 * Compares two tokens according to their category.
 *
 * Tokens from different categories are ordered by category priority.
 * Numeric tokens are compared by numeric value; all other tokens use
 * lexicographical comparison.
 */
    
    private int compareTokens(String token1, String token2) {
        int category1 = categoryOf(token1.codePointAt(0));
        int category2 = categoryOf(token2.codePointAt(0));

        if (category1 != category2) {
            return Integer.compare(category1, category2);
        }

        if (category1 == CATEGORY_DIGIT) {
            return compareDigitTokens(token1, token2);
        } else {
            return token1.compareTo(token2);
        }
    }

/**
 * Compares numeric tokens by their numeric value.
 *
 * If both tokens represent the same numeric value, the original
 * strings are compared to provide a deterministic ordering for
 * representations with leading zeros.
 */
    
    private int compareDigitTokens(String digits1, String digits2) {
        BigInteger value1 = new BigInteger(digits1);
        BigInteger value2 = new BigInteger(digits2);

        int valueComparison = value1.compareTo(value2);
        if (valueComparison != 0) {
            return valueComparison;
        }

        return digits1.compareTo(digits2);
    }
}