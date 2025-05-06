package com.bitprogress.bootserver.service.auth;

import com.bitprogress.securityspring.service.JwtTokenService;
import com.bitprogress.util.ArrayUtils;
import com.bitprogress.util.StringUtils;
import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class JwtTokenServiceImpl implements JwtTokenService {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);
    private static final String BASE_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static final String RULE = "#*J&@J(#_=*!A";

    private static Cipher encodeCipher;
    private static Cipher decodeCipher;

    /**
     * 构造函数，初始化信息
     */
    private JwtTokenServiceImpl() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(RULE.getBytes(StandardCharsets.UTF_8));
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            Key key = new SecretKeySpec(keyBytes, "AES");
            encodeCipher = Cipher.getInstance("AES");
            encodeCipher.init(Cipher.ENCRYPT_MODE, key);
            decodeCipher = Cipher.getInstance("AES");
            decodeCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("tokenUtil getInstance error ", e);
        } catch (InvalidKeyException e) {
            log.error("tokenUtil init error ", e);
        }
    }

    /**
     * 对字符串机进行编码
     *
     * @param content 原始字符串
     * @return 编码后字符串
     */
    private String encode(String content) {
        try {
            byte[] encodeResult = encodeCipher.doFinal(content.getBytes());
            return new HexBinaryAdapter().marshal(encodeResult);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("encode doFinal error ", e);
        }
        return null;
    }

    /**
     * 对字符串进行解码
     *
     * @param content 解析前的字符串
     * @return 解析后的字符串
     */
    private String decode(String content) {
        try {
            byte[] decodeResult = decodeCipher.doFinal(DatatypeConverter.parseHexBinary(content));
            return new String(decodeResult);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("decode doFinal error ", e);
        }
        return null;
    }

    /**
     * 生成需要加密的content
     *
     * @param userId 用户ID
     * @param salt   生成token的盐
     * @return 拼接后的字符串
     */
    private String generateEncodeContent(String userId, String salt) {
        return userId + "." + salt;
    }

    /**
     * 生成随机数
     *
     * @param length 生成随机数的长度
     * @return 生成的随机数
     */
    public static String randomString(int length) {
        if (StringUtils.isEmpty(BASE_STRING)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(length);
            if (length < 1) {
                length = 1;
            }

            int baseLength = BASE_STRING.length();

            for (int i = 0; i < length; ++i) {
                int number = ThreadLocalRandom.current().nextInt(baseLength);
                sb.append(BASE_STRING.charAt(number));
            }

            return sb.toString();
        }
    }

    /**
     * 创建token，只使用 userId 即可，多余信息应该另外存储
     *
     * @param userId 用户ID
     * @return token
     */
    @Override
    public String createToken(String userId) {
        String encodeContent = generateEncodeContent(userId, randomString(5));
        return encode(encodeContent);
    }

    /**
     * 获取用户ID，从token中解析用户ID
     *
     * @param token token
     * @return 用户ID
     */
    @Override
    public String getUserId(String token) {
        String decodeContent = decode(token);
        if (StringUtils.isEmpty(decodeContent)) {
            return null;
        }
        // 对token进行解析获取userId
        String[] split = decodeContent.split("\\.");
        if (ArrayUtils.isEmpty(split)) {
            return null;
        }
        return split[0];
    }

    /**
     * 加密token，与 {@link #decodeToken(String)} 对应，主要用于自定义token
     *
     * @param rawToken 需要加密成token的字符串
     * @return 加密成token的字符串
     */
    @Override
    public String encodeToken(String rawToken) {
        return encode(rawToken);
    }

    /**
     * 解析token
     *
     * @param token 需要解析的token
     * @return token解析后的字符串
     */
    @Override
    public String decodeToken(String token) {
        return decode(token);
    }

}
