package com.bitprogress.formatter;

import com.bitprogress.util.LocalDateTimeUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    /**
     * Parse a text String to produce a T.
     *
     * @param text   the text string
     * @param locale the current user locale
     * @return an instance of T
     * @throws ParseException           when a parse exception occurs in a java.text parsing library
     * @throws IllegalArgumentException when a parse exception occurs
     */
    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTimeUtils.parse(text);
    }

    /**
     * Print the object of type T for display.
     *
     * @param object the instance to print
     * @param locale the current user locale
     * @return the printed text string
     */
    @Override
    public String print(LocalDateTime object, Locale locale) {
        return LocalDateTimeUtils.format(object);
    }

}
