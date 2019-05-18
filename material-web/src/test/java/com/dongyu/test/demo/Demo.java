package com.dongyu.test.demo;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * TODO:请添加描述
 *
 * @author TYF
 * @date 2019/5/9
 * @since 1.0.0
 */
public class Demo {
    private static final String KEY = "xxxx";//从服务器要的密钥

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * 解密
     * @param data
     *            待解密内容
     * @return
     */
    public static byte[] decrypt(byte[] data) throws Exception {

        Key k = toKey(KEY.getBytes());

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    private static Key toKey(byte[] key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key, "AES");

        return secretKey;
    }
//    public static byte[] aesDecode(byte[] content, String pkey) throws Exception {
//
//        //SecretKey secretKey = generateKey(pkey);
//        //byte[] enCodeFormat = secretKey.getEncoded();
//        SecretKeySpec skey = new SecretKeySpec(pkey.getBytes(), "AES");
//        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");// 创建密码器
//        cipher.init(Cipher.DECRYPT_MODE, skey, iv);// 初始化解密器
//        byte[] result = cipher.doFinal(content);
//        return result; // 解密
//
//    }
}
