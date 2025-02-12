package com.bitprogress.auth.base;

import com.bitprogress.exception.CommonException;
import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * token工具类
 */
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private static Cipher encodeCipher;
    private static Cipher decodeCipher;

    /**
     * 构造函数，初始化信息
     */
    private TokenUtils() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            String rule = "#*J&@J(#_=*!A";
            secureRandom.setSeed(rule.getBytes(StandardCharsets.UTF_8));
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            Key key = new SecretKeySpec(keyBytes, "AES");
            encodeCipher = Cipher.getInstance("AES");
            encodeCipher.init(Cipher.ENCRYPT_MODE, key);
            decodeCipher = Cipher.getInstance("AES");
            decodeCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            log.error("TokenUtils init error ", e);
            throw CommonException.error("TokenUtils init error");
        }
    }

    public static String encode(String content) {
        try {
            byte[] encodeResult = encodeCipher.doFinal(content.getBytes());
            return new HexBinaryAdapter().marshal(encodeResult);
        } catch (Exception e) {
            log.error("TokenUtils encode error ", e);
        }
        return null;
    }

    public static String decode(String content) {
        try {
            byte[] decodeResult = decodeCipher.doFinal(DatatypeConverter.parseHexBinary(content));
            return new String(decodeResult);
        } catch (Exception e) {
            log.error("TokenUtils decode error ", e);
        }
        return null;
    }

}
