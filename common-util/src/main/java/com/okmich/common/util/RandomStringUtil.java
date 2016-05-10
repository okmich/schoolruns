/**
 *
 */
package com.okmich.common.util;

import java.util.Random;

/**
 * @author Michael Enudi
 *
 */
public final class RandomStringUtil {

    /**
     * A string use to generate a random password.
     */
    private static final String RANDOMSTRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * A string use to generate a random numbers-only string.
     */
    private static final String RANDOMNUMERICSTRING = "0123456789";
    /**
     * A string use to generate a random alphabet-only string.
     */
    private static final String RANDOMALPHASTRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private RandomStringUtil() {
    }

    /**
     * Generate a random sequence of characters of length {@code length}. The
     * character would only belong to the regex group [A-Z][0-9].
     *
     * @return Returns the random sequence of characters.
     */
    public static String generateRandomString(int length) {
        return generateRandomString(RANDOMSTRING, length);
    }

    /**
     * Generate a random sequence of numeric characters of length
     * {@code length}. The character would only belong to the regex group [0-9].
     *
     * @return Returns the random sequence of characters.
     */
    public static String generateRandomNumericString(int length) {
        return generateRandomString(RANDOMNUMERICSTRING, length);
    }

    /**
     * Generate a random sequence of english alphabet-only characters of length
     * {@code length}. The character would only belong to the regex group [A-Z].
     *
     * @return Returns the random sequence of characters.
     */
    public static String generateRandomAlphaString(int length) {
        return generateRandomString(RANDOMALPHASTRING, length);
    }

    /**
     *
     * @param source
     * @param length
     * @return
     */
    private static String generateRandomString(String source, int length) {
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(source.charAt(random.nextInt(source
                    .length())));
        }
        return password.toString();
    }
}
