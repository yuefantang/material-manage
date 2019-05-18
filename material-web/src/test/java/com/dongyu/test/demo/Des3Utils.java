package com.dongyu.test.demo;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * 3DESECB加密
 *
 * @author TYF
 * @date 2019/5/12
 * @since 1.0.0
 */
public class Des3Utils {
    public static final String KEY = "aibizhall-biz-zzyyt-1234";
    // 3DESECB加密,key必须是长度大于等于 3*8 = 24 位哈
    public static String encryptThreeDESECB(String src) throws Exception {
        final DESedeKeySpec dks = new DESedeKeySpec(KEY.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);

        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        final byte[] b = cipher.doFinal(src.getBytes());

        final BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

    }

    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
    public static String decryptThreeDESECB(String src) throws Exception {
        // --通过base64,将字符串转成byte数组
        final BASE64Decoder decoder = new BASE64Decoder();
        final byte[] bytesrc = decoder.decodeBuffer(src);
        // --解密的key
        final DESedeKeySpec dks = new DESedeKeySpec(KEY.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }
    public static void main(String[] args) throws Exception {
        String a = Des3Utils.encryptThreeDESECB("李四");
        System.out.println("a= "+a);
        String b = Des3Utils.decryptThreeDESECB("52vZEiJ33R4=");
        System.out.println("b= "+b);
    }
}
