package com.bitprogress.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author wuwuwupx
 */
public class Base64Utils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Base64-encode the given byte array.
     * @param src the original byte array
     * @return the encoded byte array
     * @throws IllegalStateException if Base64 encoding between byte arrays is not
     * supported, i.e. neither Java 8 nor Apache Commons Codec is present at runtime
     */
    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getEncoder().encode(src);
    }

    /**
     * Base64-decode the given byte array.
     * @param src the encoded byte array
     * @return the original byte array
     * @throws IllegalStateException if Base64 encoding between byte arrays is not
     * supported, i.e. neither Java 8 nor Apache Commons Codec is present at runtime
     */
    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getDecoder().decode(src);
    }

    /**
     * Base64-encode the given byte array using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the original byte array
     * @return the encoded byte array
     * @throws IllegalStateException if Base64 encoding between byte arrays is not
     * supported, i.e. neither Java 8 nor Apache Commons Codec is present at runtime
     * @since 4.2.4
     */
    public static byte[] encodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlEncoder().encode(src);
    }

    /**
     * Base64-decode the given byte array using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the encoded byte array
     * @return the original byte array
     * @throws IllegalStateException if Base64 encoding between byte arrays is not
     * supported, i.e. neither Java 8 nor Apache Commons Codec is present at runtime
     * @since 4.2.4
     */
    public static byte[] decodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlDecoder().decode(src);
    }

    /**
     * Base64-encode the given byte array to a String.
     * @param src the original byte array (may be {@code null})
     * @return the encoded byte array as a UTF-8 String
     */
    public static String encodeToString(byte[] src) {
        if (src.length == 0) {
            return "";
        }
        return new String(encode(src), DEFAULT_CHARSET);
    }

    /**
     * Base64-decode the given byte array from an UTF-8 String.
     * @param src the encoded UTF-8 String
     * @return the original byte array
     */
    public static byte[] decodeFromString(String src) {
        if (src.isEmpty()) {
            return new byte[0];
        }
        return decode(src.getBytes(DEFAULT_CHARSET));
    }

    /**
     * Base64-encode the given byte array to a String using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the original byte array
     * @return the encoded byte array as a UTF-8 String
     * @throws IllegalStateException if Base64 encoding between byte arrays is not
     * supported, i.e. neither Java 8 nor Apache Commons Codec is present at runtime
     */
    public static String encodeToUrlSafeString(byte[] src) {
        return new String(encodeUrlSafe(src), DEFAULT_CHARSET);
    }

    /**
     * Base64-decode the given byte array from an UTF-8 String using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the encoded UTF-8 String
     * @return the original byte array
     * @throws IllegalStateException if Base64 encoding between byte arrays is not
     * supported, i.e. neither Java 8 nor Apache Commons Codec is present at runtime
     */
    public static byte[] decodeFromUrlSafeString(String src) {
        return decodeUrlSafe(src.getBytes(DEFAULT_CHARSET));
    }

}
