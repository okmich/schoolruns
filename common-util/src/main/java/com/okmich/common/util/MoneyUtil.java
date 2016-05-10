package com.okmich.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Patrick Aniah
 */
public class MoneyUtil implements Serializable {

    private String currency;
    private String subcurrency;
    private String[] moneyTerms = {
        "Hundred", "Thousand",
        "Million", "Billion",
        "Trillion", "Quadrillion"
    };

    /**
     * Creates a new instance of MoneyUtility
     */
    public MoneyUtil() {
    }

    public static String getMoneyDigitsInStandardForm(double amountInDigits) {
        DecimalFormat moneyFormat = new DecimalFormat();
        moneyFormat.applyPattern("#,#00.00");
        return moneyFormat.format(amountInDigits);
    }

    /**
     *
     * @param amountStr
     * @return
     */
    public static double getMoneyInNumericForm(String amountStr) {
        String moneyAmount = "";

        for (int i = 0; i <= amountStr.length() - 1; i++) {
            if ((amountStr.charAt(i) != ',') && (amountStr.charAt(i) != ' ')) {
                moneyAmount += String.valueOf(amountStr.charAt(i));
            }
        }

        return Double.parseDouble(moneyAmount);
    }

    public static String convertToWords(BigDecimal amount) {
        MoneyUtil util = new MoneyUtil();
        return util.mConvertToWords(amount.doubleValue());
    }

    public String mConvertToWords(double amountInDigits)
            throws MoneyOutOfRangeException {
        String amountInWords = "";

        String mCurrency = getMoneyDigitsInStandardForm(amountInDigits);
        String nairaValue = getDigitsBeforeDecimal(mCurrency);
        String koboValue = getDigitsAfterDecimal(mCurrency);

        String[] digitTerms = nairaValue.split(",");
        String[] terms = getTerms(digitTerms.length);

        if (terms.length == 6) {
            MoneyOutOfRangeException moore =
                    new MoneyOutOfRangeException("Money out of range Exception: "
                    + nairaValue + "." + koboValue + " is above a Quadrillion!");
            moore.setMoneyValue(nairaValue + "." + koboValue);
            throw moore;
        }

        for (int i = 0; i <= terms.length - 1; i++) {
            if (isInHundreds(digitTerms[i])) {
                String hundredsDigit = getDigitName(
                        String.valueOf(digitTerms[i].charAt(0)));
                String tensDigit = getDigitName(
                        String.valueOf(Integer.parseInt(digitTerms[i].substring(1))));
                if (!tensDigit.trim().equals("")) {
                    amountInWords += hundredsDigit + " Hundred And " + tensDigit + " " + terms[i] + " ";
                } else {
                    amountInWords += hundredsDigit + " Hundred " + terms[i] + " ";
                }
            } else {
                String tensDigit = getDigitName(String.valueOf(Integer.parseInt(digitTerms[i])));
                if (!tensDigit.trim().equals("")) {
                    amountInWords += "And " + tensDigit + " " + terms[i] + " ";
                }
            }
        }
        if (amountInWords.substring(0, 3).equalsIgnoreCase("And")) {
            amountInWords = amountInWords.substring(4);
        }

        if (amountInWords.substring(amountInWords.length() - 8,
                amountInWords.length()).trim().equalsIgnoreCase("Hundred")) {
            amountInWords = amountInWords.substring(0, amountInWords.length() - 8).trim();
        }
        /*if(amountInWords.substring(amountInWords.length()-3, amountInWords.length()).trim().equalsIgnoreCase("And")){
         amountInWords = amountInWords.substring(0, amountInWords.length()-3).trim();
         }*/

        String koboInWords = getDigitName(String.valueOf(Integer.parseInt(koboValue)));

        amountInWords = appendCurrencyAndSubcurrency(amountInWords, koboInWords);

        return amountInWords;
    }

    private String getDigitsBeforeDecimal(String currency) {
        return currency.substring(0, currency.indexOf("."));
    }

    private String getDigitsAfterDecimal(String currency) {
        String afterDecimal = currency.substring(currency.indexOf(".") + 1, currency.length());
        if (afterDecimal.length() >= 2) {
            afterDecimal = afterDecimal.substring(0, 2);
        } else if (afterDecimal.length() == 1) {
            afterDecimal = afterDecimal.substring(0, 1) + "0";
        } else {
            afterDecimal = "00";
        }

        return afterDecimal;
    }

    private String appendCurrencyAndSubcurrency(String nairaValue, String koboValue) {
        String nairaAndKoboValue;

        if (koboValue.trim().equals("")) {
            nairaAndKoboValue = nairaValue.trim() + " " + getCurrency() + ".";
        } else {
            nairaAndKoboValue = nairaValue.trim() + " " + getCurrency() + ", " + koboValue + " " + getSubcurrency() + ".";
        }

        return nairaAndKoboValue;
    }

    private String[] getTerms(int termLength) {
        String[] terms = new String[termLength];

        if (termLength == 1) {
            terms[0] = "Hundred";
        } else if (termLength == 2) {
            terms[0] = "Thousand";
            terms[1] = "Hundred";
        } else if (termLength == 3) {
            terms[0] = "Million";
            terms[1] = "Thousand";
            terms[2] = "Hundred";
        } else if (termLength == 4) {
            terms[0] = "Billion";
            terms[1] = "Million";
            terms[2] = "Thousand";
            terms[3] = "Hundred";
        } else if (termLength == 5) {
            terms[0] = "Trillion";
            terms[1] = "Billion";
            terms[2] = "Million";
            terms[3] = "Thousand";
            terms[4] = "Hundred";
        } else if (termLength == 6) {
            terms[0] = "Quadrillion";
            terms[1] = "Trillion";
            terms[2] = "Billion";
            terms[3] = "Million";
            terms[4] = "Thousand";
            terms[5] = "Hundred";
        }

        return terms;
    }

    private static boolean isTeen(String digitStr) {
        int remainder = Integer.parseInt(digitStr) % 10;
        return ((remainder >= 3) && (remainder <= 9) && (Integer.parseInt(digitStr) / 10 == 1));
    }

    private boolean isTies(String digitStr) {
        int dividend = Integer.parseInt(digitStr) / 10;
        return (dividend >= 2);
    }

    private boolean isInHundreds(String digitStr) {
        return (Integer.parseInt(digitStr) >= 100);
    }

    private String getDigitName(String digitStr) {
        String digitName = "";

        if (digitStr.equals("1")) {
            digitName = "One";
        } else if (digitStr.equals("2")) {
            digitName = "Two";
        } else if (digitStr.equals("3")) {
            digitName = "Three";
        } else if (digitStr.equals("4")) {
            digitName = "Four";
        } else if (digitStr.equals("5")) {
            digitName = "Five";
        } else if (digitStr.equals("6")) {
            digitName = "Six";
        } else if (digitStr.equals("7")) {
            digitName = "Seven";
        } else if (digitStr.equals("8")) {
            digitName = "Eight";
        } else if (digitStr.equals("9")) {
            digitName = "Nine";
        } else if (digitStr.equals("10")) {
            digitName = "Ten";
        } else if (digitStr.equals("11")) {
            digitName = "Eleven";
        } else if (digitStr.equals("12")) {
            digitName = "Twelve";
        } else if (isTeen(digitStr)) {
            if (Integer.parseInt(digitStr) % 10 == 3) {
                digitName = "Thirteen";
            } else if (Integer.parseInt(digitStr) % 10 == 5) {
                digitName = "Fifteen";
            } else if (Integer.parseInt(digitStr) % 10 == 8) {
                digitName = "Eighteen";
            } else {
                digitName = getDigitName(String.valueOf(Integer.parseInt(digitStr) % 10)) + "teen";
            }
        } else if (isTies(digitStr)) {
            if (Integer.parseInt(digitStr) / 10 == 2) {
                digitName = "Twenty " + getDigitName(String.valueOf(Integer.parseInt(digitStr) % 10));
            } else if (Integer.parseInt(digitStr) / 10 == 3) {
                digitName = "Thirty " + getDigitName(String.valueOf(Integer.parseInt(digitStr) % 10));
            } else if (Integer.parseInt(digitStr) / 10 == 5) {
                digitName = "Fifty " + getDigitName(String.valueOf(Integer.parseInt(digitStr) % 10));
            } else if (Integer.parseInt(digitStr) / 10 == 8) {
                digitName = "Eighty " + getDigitName(String.valueOf(Integer.parseInt(digitStr) % 10));
            } else if (Integer.parseInt(digitStr) / 10 >= 4) {
                digitName = getDigitName(String.valueOf(Integer.parseInt(digitStr) / 10)) + "ty " + getDigitName(String.valueOf(Integer.parseInt(digitStr) % 10));
            }
        }

        return digitName;
    }

    private String getDigitTerm(String wordTerm) {
        String digitTerm = "";

        if (wordTerm.equalsIgnoreCase("Hundred")) {
            digitTerm = "00";
        } else if (wordTerm.equalsIgnoreCase("Thousand")) {
            digitTerm = "000";
        } else if (wordTerm.equalsIgnoreCase("Million")) {
            digitTerm = "000000";
        } else if (wordTerm.equalsIgnoreCase("Billion")) {
            digitTerm = "000000000";
        } else if (wordTerm.equalsIgnoreCase("Trillion")) {
            digitTerm = "000000000000";
        } else if (wordTerm.equalsIgnoreCase("Quadrillion")) {
            digitTerm = "000000000000000";
        }

        return digitTerm;
    }

    private String getDigitOfName(String digitName) {
        String digitOfName = "";

        if (digitName.equalsIgnoreCase("One")) {
            digitOfName = "1";
        } else if (digitName.equalsIgnoreCase("Two")) {
            digitOfName = "2";
        } else if (digitName.equalsIgnoreCase("Three")) {
            digitOfName = "3";
        } else if (digitName.equalsIgnoreCase("Four")) {
            digitOfName = "4";
        } else if (digitName.equalsIgnoreCase("Five")) {
            digitOfName = "5";
        } else if (digitName.equalsIgnoreCase("Six")) {
            digitOfName = "6";
        } else if (digitName.equalsIgnoreCase("Seven")) {
            digitOfName = "7";
        } else if (digitName.equalsIgnoreCase("Eight")) {
            digitOfName = "8";
        } else if (digitName.equalsIgnoreCase("Nine")) {
            digitOfName = "9";
        } else if (digitName.equalsIgnoreCase("Ten")) {
            digitOfName = "10";
        } else if (digitName.equalsIgnoreCase("Eleven")) {
            digitOfName = "11";
        } else if (digitName.equalsIgnoreCase("Twelve")) {
            digitOfName = "12";
        } else if (digitName.equalsIgnoreCase("Thirteen")) {
            digitOfName = "13";
        } else if (digitName.equalsIgnoreCase("Fourteen")) {
            digitOfName = "14";
        } else if (digitName.equalsIgnoreCase("Fifteen")) {
            digitOfName = "15";
        } else if (digitName.equalsIgnoreCase("Sixteen")) {
            digitOfName = "16";
        } else if (digitName.equalsIgnoreCase("Seventeen")) {
            digitOfName = "17";
        } else if (digitName.equalsIgnoreCase("Eighteen")) {
            digitOfName = "18";
        } else if (digitName.equalsIgnoreCase("Nineteen")) {
            digitOfName = "19";
        } else if (digitName.equalsIgnoreCase("Twenty")) {
            digitOfName = "20";
        } else if (digitName.equalsIgnoreCase("Thirty")) {
            digitOfName = "30";
        } else if (digitName.equalsIgnoreCase("Fourty")) {
            digitOfName = "40";
        } else if (digitName.equalsIgnoreCase("Fifty")) {
            digitOfName = "50";
        } else if (digitName.equalsIgnoreCase("Sixty")) {
            digitOfName = "60";
        } else if (digitName.equalsIgnoreCase("Seventy")) {
            digitOfName = "70";
        } else if (digitName.equalsIgnoreCase("Eighty")) {
            digitOfName = "80";
        } else if (digitName.equalsIgnoreCase("Ninety")) {
            digitOfName = "90";
        }

        return digitOfName;
    }

    /**
     * Conversion from words to digits.....
     *
     */
    private String[] toArray(String strValue) {
        String[] valueList;
        String spoofedContent = "";

        for (int i = 0; i <= strValue.length() - 1; i++) {
            if (strValue.charAt(i) != ',') {
                spoofedContent += String.valueOf(strValue.charAt(i));
            }
        }
        valueList = spoofedContent.split(" ");

        return valueList;
    }

    boolean isTerm(String field) {
        boolean termBool = false;

        for (int i = 0; i <= moneyTerms.length - 1; i++) {
            if (field.equalsIgnoreCase(moneyTerms[i])) {
                termBool = true;
                i = moneyTerms.length;
            }
        }

        return termBool;
    }

    private String getBeforeCurrency(String amountInWords) {
        return amountInWords.substring(0, amountInWords.indexOf(getCurrency()));
    }

    private String getBeforeSubcurrency(String amountInWords) {
        return amountInWords.substring(amountInWords.indexOf(getCurrency() + ", ") + 6, amountInWords.indexOf(getSubcurrency())).trim();
    }

    private String appendDigits(String amountInDigits, String amountTerm) {
        String appendedDigits = "";
        for (int i = 0; i <= amountInDigits.length() - amountTerm.length() - 1; i++) {
            appendedDigits += String.valueOf(amountInDigits.charAt(i));
        }

        return (appendedDigits + amountTerm);
    }

    private String[] getDimension(String largestTerm) {
        String[] termDim = null;
        if (largestTerm.equalsIgnoreCase("Quadrillion")) {
            termDim = new String[6];
        } else if (largestTerm.equalsIgnoreCase("Trillion")) {
            termDim = new String[5];
        } else if (largestTerm.equalsIgnoreCase("Billion")) {
            termDim = new String[4];
        } else if (largestTerm.equalsIgnoreCase("Million")) {
            termDim = new String[3];
        } else if (largestTerm.equalsIgnoreCase("Thousand")) {
            termDim = new String[2];
        }

        if (termDim != null) {
            for (int i = 0; i <= termDim.length - 1; i++) {
                termDim[i] = "";
            }
        }

        return termDim;
    }

    private String getLargestTerm(String[] amountArray) {
        String largestTerm = "";

        for (int i = 0; i <= amountArray.length - 1; i++) {
            if (isTerm(amountArray[i])) {
                if (!amountArray[i].equalsIgnoreCase("Hundred")) {
                    largestTerm = amountArray[i];
                    i = amountArray.length;
                }
            }
        }

        return largestTerm;
    }

    public String wordToDigitConverter(String amountInWords) {
        String amountTerm = "";
        int count = 0;
        String[] amountArray = toArray(amountInWords);
        String[] termArray = getDimension(getLargestTerm(amountArray));

        if (termArray == null) {
            termArray = new String[1];
            termArray[0] = "";
        }

        for (int i = 0; i <= amountArray.length - 1; i++) {
            if (!amountArray[i].equalsIgnoreCase("And")) {
                if (isTerm(amountArray[i])) {
                    if (amountArray[i].equalsIgnoreCase("Hundred")) {
                        amountTerm += getDigitTerm(amountArray[i]);
                    } else {
                        termArray[count++] = amountTerm + getDigitTerm(amountArray[i]);
                        amountTerm = "";
                    }
                } else {
                    amountTerm = appendDigits(amountTerm, getDigitOfName(amountArray[i]));
                }
            }
        }

        if (!amountTerm.trim().equals("")) {
            termArray[termArray.length - 1] = appendDigits(termArray[termArray.length - 1], amountTerm);
        }
        for (int i = 0; i <= termArray.length - 1; i++) {
            if (i == 0) {
                amountTerm = termArray[termArray.length - i - 1];
            } else {
                amountTerm = appendDigits(termArray[termArray.length - i - 1], amountTerm);
            }
        }

        return amountTerm;
    }

    private String removeDelimiters(String amountInWords) {
        String unDelimited = "";
        for (int i = 0; i <= amountInWords.length() - 1; i++) {
            if ((amountInWords.charAt(i) != ',') && (amountInWords.charAt(i) != '.')) {
                unDelimited += String.valueOf(amountInWords.charAt(i));
            }
        }

        return unDelimited;
    }

    public String convertToDigits(String amountInWords) throws UnknownMoneyElementException {
        String unDelimited = removeDelimiters(amountInWords);
        String[] amountArray = toArray(unDelimited);

        for (int i = 0; i <= amountArray.length - 1; i++) {
            if ((!amountArray[i].equalsIgnoreCase(getCurrency())) && (!amountArray[i].equalsIgnoreCase(getSubcurrency())) && (!amountArray[i].equalsIgnoreCase("And"))) {
                if ((getDigitTerm(amountArray[i]).trim().equals("")) && (getDigitOfName(amountArray[i]).trim().equals(""))) {
                    int pos = i;
                    i = amountArray.length;
                    UnknownMoneyElementException umee = new UnknownMoneyElementException("UnknownMoneyElementException: The element (" + amountArray[pos] + ") in the money String cannot be represented in numerical form ");
                    umee.setUnknownMoneyElement(amountArray[pos]);
                    throw umee;
                }
            }
        }

        amountInWords = getMoneyWordsInStandardForm(amountInWords);
        // amountArray = toArray(amountInWords);

        String nairaValue = getBeforeCurrency(amountInWords);
        String koboValue = getBeforeSubcurrency(amountInWords);
        String digitAmount = wordToDigitConverter(nairaValue) + "." + wordToDigitConverter(koboValue);

        return digitAmount;
    }

    private String getMoneyWordsInStandardForm(String amountInWords) {
        if (amountInWords.charAt(amountInWords.indexOf(getCurrency()) + getCurrency().length()) != ',') {
            amountInWords = amountInWords.substring(0, amountInWords.indexOf(getCurrency()) + getCurrency().length() - 1) + "," + amountInWords.substring(amountInWords.indexOf(getCurrency()) + getCurrency().length());
        }

        return amountInWords;
    }

    public String getCurrency() {
        return currency == null ? "Naira" : currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubcurrency() {
        return subcurrency == null ? "Kobo" : currency;
    }

    public void setSubcurrency(String subcurrency) {
        this.subcurrency = subcurrency;
    }
}
