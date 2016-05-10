package com.okmich.common.util;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * handles various utility function for String. These functions are not present
 * in Sun Java JDK API.
 *
 * @author Michael Enudi
 *
 * @compay Leadway Assurance Company Ltd.
 * @since February 15, 2008, 4:10 AM
 */
public class StringUtil {

    /**
     * Creates a new instance of StringUtil
     */
    public StringUtil() {
    }

    /**
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        return !(string != null && !string.trim().isEmpty());
    }

    /**
     * creates a random String of length {@code length} which is actually the
     * last length digit of {@code System.nanoTime()}
     *
     * @param length
     * @return String
     */
    public static String getIDFromNanoTime(int length) {
        String ids = String.valueOf(System.nanoTime());
        return ids.substring(ids.length() - length);
    }


    /**
     * returns a message within an exception if present. else returns the
     * message "TECH_EX"
     *
     * @param ex
     * @return String
     */
    public static String getNestedErrorMessage(Exception ex) {
        String errorMsg = (ex.getMessage() == null || ex.getMessage().equals("")
                ? "ERROR_CONTACT_ADMINISTRATOR" : ex.getMessage());
        return errorMsg;
    }

    /**
     * receives a string and pads the string to the left.
     *
     * @param String - the String to be left padded
     * @param int - the expected length for the resulting string
     * @param char - the char used to pad
     * @return String - the padded String
     */
    public static String leftPad(String basicString, int finalStringLength, char padChar) {
        StringBuilder buffer = new StringBuilder();
        int diff = finalStringLength - basicString.length();
        for (int i = 0; i < diff; i++) {
            buffer.append(padChar);
        }
        buffer.append(basicString);
        return buffer.toString();
    }

    /**
     * parses the string and removes every white space
     *
     * @param string
     * @return String
     */
    public static String removeWhiteSpaces(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        if (string.equals("")) {
            return "";
        }
        return string.replaceAll("\\s", "");
    }

    /**
     * converts the local Nigerian mobile number format to that of the regex
     * form {@code \\+\\d{13}}
     *
     * @param mobileNo
     * @return the formatted mobile number
     */
    public static String formatNigerianLocalMobileNumber(String mobileNo) {
        if (mobileNo == null) {
            return null;
        }
        mobileNo = mobileNo.trim().replaceAll("\\s", "");
        if (!Pattern.matches("\\+\\d{13}", mobileNo)
                && mobileNo.startsWith("0")) {
            return mobileNo.replaceFirst("0", "+234");
        }
        return mobileNo;
    }

    /**
     * returns a details representation of all the fields that make up the
     * object passed into it. It should be used in any application or module
     * that wants this representation in the toString() of their objects.
     *
     * @param Object - object to be represented
     * @return String - the objects representation
     *
     */
    public static String toString(Object object) {
        Class clazz = object.getClass();
        StringBuilder builder = new StringBuilder("<" + clazz.getName() + ">\n");
        Field[] fields = Util.getInstanceVariables(clazz);
        for (int i = 0; i < fields.length; i++) {
            Field currentField = fields[i];
            currentField.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = currentField.get(object);
            } catch (IllegalArgumentException ex) {
                //do nothing
            } catch (IllegalAccessException ex) {
                //do nothing
            }
            builder.append("    <").append(currentField.getName()).append(">").append(fieldValue).
                    append("</").append(currentField.getName()).append(">\n");
        }
        builder.append("</").append(clazz.getName()).append(">");
        return builder.toString();
    }
}
