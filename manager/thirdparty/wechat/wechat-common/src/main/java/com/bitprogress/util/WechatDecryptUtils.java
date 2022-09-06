package com.bitprogress.util;

import com.bitprogress.exception.WechatException;
import com.bitprogress.exception.WechatExceptionMessage;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;

/**
 * 微信相关解密
 *
 * @author wuwuwupx
 */
public class WechatDecryptUtils {

    /**
     * AES解密,返回null表示解密失败.
     *
     * @param encryptedData 密文
     * @param key           秘钥
     * @param iv            偏移量
     * @return 解密后的数据 or {@code null}
     */
    public static String decrypt(String encryptedData, String key, String iv) {
        Assert.isNotEmpty(encryptedData, WechatExceptionMessage.ENCRYPTED_DATA_EMPTY_EXCEPTION);
        //待解密数据,加密秘钥,偏移量
        byte[] dataByte = Base64Utils.decode(encryptedData.getBytes());
        byte[] keyByte = Base64Utils.decode(key.getBytes());
        byte[] ivByte = Base64Utils.decode(iv.getBytes());
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            return ArrayUtils.isEmpty(resultByte) ? null : new String(resultByte, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new WechatException(WechatExceptionMessage.DATA_ENCRYPTED_EXCEPTION);
        }
    }

}
