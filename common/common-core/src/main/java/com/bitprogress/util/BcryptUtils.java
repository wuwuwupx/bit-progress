package com.bitprogress.util;

import com.bitprogress.constant.StringConstants;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * @author wuwuwupx
 */
public class BcryptUtils {

    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    private static Cipher cipher;

    private static KeyFactory keyFactory;

    private static String PATH = System.getProperty("java.io.tmpdir") + File.separator + "bit-progress" + File.separator
            + "file" + File.separator;

    static {
        try {
            cipher = Cipher.getInstance(StringConstants.CIPHER_RSA);
            keyFactory = KeyFactory.getInstance(StringConstants.CIPHER_RSA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] hellos = encrypt("3a16a747489c95e083f57d8deb76d854", "security/oaplatform_pub_key.pen");
        System.out.println(encoder.encodeToString(hellos));
    }

    /**
     * MD5加密
     *
     * @param content
     * @return
     */
    public static String MD5(String content) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] hashCode = messageDigest.digest();
        return new HexBinaryAdapter().marshal(hashCode).toLowerCase();
    }

    /**
     * @param content
     * @return
     */
    public static String decrypt(String content) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decrypt(content, "security/pri_key.pen");
    }

    /**
     * @param content
     * @return
     */
    public static byte[] encrypt(String content) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return encrypt(content, "security/pub_key.pen");
    }

    /**
     * @param content
     * @return
     */
    public static String decrypt(String content, String filePath) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        InputStream resourceAsStream = BcryptUtils.class.getClassLoader().getResourceAsStream(filePath);
        File file = new File(PATH);
        FileUtils.copyInputStreamToFile(resourceAsStream, file);
        RSAPrivateKey rsaPrivateKey = loadPrivateKeyByFile(file);
        return pri_key_decode(decoder.decode(content), rsaPrivateKey);
    }

    /**
     * @param content
     * @return
     */
    public static byte[] encrypt(String content, String filePath) throws IOException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        InputStream resourceAsStream = BcryptUtils.class.getClassLoader().getResourceAsStream(filePath);
        File file = new File(PATH);
        FileUtils.copyInputStreamToFile(resourceAsStream, file);
        RSAPublicKey rsaPublicKey = loadPublicKeyByFile(file);
        return pub_key_encode(content, rsaPublicKey);
    }

    /**
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            byte[] resultDatas = out.toByteArray();
            return resultDatas;
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        } finally {
            if (Objects.nonNull(out)) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param file
     * @return
     */
    private static RSAPublicKey loadPublicKeyByFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String key = sb.toString();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoder.decode(key));
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param file
     * @return
     */
    private static RSAPrivateKey loadPrivateKeyByFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String key = sb.toString();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoder.decode(key));
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param content
     * @param pri_key
     * @return
     */
    public static byte[] pri_key_encode(String content, RSAPrivateKey pri_key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, pri_key);
        return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param content
     * @param pub_key
     * @return
     */
    private static byte[] pub_key_encode(String content, RSAPublicKey pub_key) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, pub_key);
        return rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, content.getBytes(StandardCharsets.UTF_8), pub_key.getModulus().bitLength());
    }


    /**
     * @param content
     * @param pri_key
     * @return
     */
    private static String pri_key_decode(byte[] content, RSAPrivateKey pri_key) throws InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, pri_key);
        return new String(rsaSplitCodec(cipher, cipher.DECRYPT_MODE, content, pri_key.getModulus().bitLength()));
    }


    /**
     * @param content
     * @param pub_key
     * @return
     */
    public static String pub_key_decode(byte[] content, RSAPublicKey pub_key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, pub_key);
        return new String(cipher.doFinal(content), StandardCharsets.UTF_8);
    }

}
