package com.test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*******************************************************************************
 * AES加解密算法
 *
 * @author arix04
 *
 */

public class Aes {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }

        byte[] raw = sKey.getBytes("UTF-8");//"ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        byte[] arrayOfByte = new byte[16];
        // byte [] paramString2 = paramString2.getBytes("UTF-8");
        int j = raw.length;
        int i = j;
        if (j > arrayOfByte.length) {
            i = arrayOfByte.length;
        }
        System.arraycopy(raw, 0, arrayOfByte, 0, i);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(arrayOfByte);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE,  new SecretKeySpec(arrayOfByte, "AES"), iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");//"ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] arrayOfByte = new byte[16];
           // byte [] paramString2 = paramString2.getBytes("UTF-8");
            int j = raw.length;
            int i = j;
            if (j > arrayOfByte.length) {
                i = arrayOfByte.length;
            }
            System.arraycopy(raw, 0, arrayOfByte, 0, i);
            IvParameterSpec iv = new IvParameterSpec(arrayOfByte);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(arrayOfByte, "AES"), iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
         * 此处使用AES-128-CBC加密模式，key需要为16位。
         */
        String cKey = "tjdflklwer,.sdfsfljkjerersdfsf.,.,.sd,f.sd,f.sdf,.dsserfserf";
        // 需要加密的字串
        String cSrc = "Email : arix04@xxx.com";

        //需要解密的数据
        String csrc2 = "seOBn/IKs0M4HTtWEWJrdzch/ke1IQ/og455zGKy+m/3/oQ+2YZY+6Au80fWZO0hubaF138BL/lWEVApxa/sYPJyIxy1AmxbaK/ANdaEMwuY09Nm8uejhgTjVhwglR8k";
        System.out.println("需要解密的数据："+csrc2);
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = Aes.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        String DeString = Aes.Decrypt(csrc2, cKey);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }
}