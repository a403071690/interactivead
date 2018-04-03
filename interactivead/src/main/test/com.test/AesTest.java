package com.test;

//import com.zc.util.Base64Util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesTest {

    public static void main(String[] args) throws Exception{
        // 密钥的种子，可以是任何形式，本质是字节数组
        String strKey = "tjdflklwer,.sdfsfljkjerersdfsf.,.,.sd,f.sd,f.sdf,.dsserfserf";
        // 密钥数据
        byte[] rawKey = getRawKey(strKey.getBytes());
        // 密码的明文
        String clearPwd = "seOBn/IKs0M4HTtWEWJrdzch/ke1IQ/og455zGKy+m/3/oQ+2YZY+6Au80fWZO0hubaF138BL/lWEVApxa/sYPJyIxy1AmxbaK/ANdaEMwuY09Nm8uejhgTjVhwglR8k";
        // 密码加密后的密文

       // byte[] encryptedByteArr = encrypt(rawKey, clearPwd);
        //String encryptedPwd = new String(encryptedByteArr);
        byte [] aaa = "seOBn/IKs0M4HTtWEWJrdzch/ke1IQ/og455zGKy+m/3/oQ+2YZY+6Au80fWZO0hubaF138BL/lWEVApxa/sYPJyIxy1AmxbaK/ANdaEMwuY09Nm8uejhgTjVhwglR8k".getBytes();

       // byte []sss = Base64Util.decode(clearPwd); // System.out.println(encryptedPwd);
        // 解密后的字符串
     //   String decryptedPwd = decrypt(sss, rawKey);
      //  System.out.println(decryptedPwd);

    }

    /**
     * @param rawKey
     *            密钥
     * @param clearPwd
     *            明文字符串
     * @return 密文字节数组
     */
    private static byte[] encrypt(byte[] rawKey, String clearPwd) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encypted = cipher.doFinal(clearPwd.getBytes());
            return encypted;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param encrypted
     *            密文字节数组
     * @param rawKey
     *            密钥
     * @return 解密后的字符串
     */
    private static String decrypt(byte[] encrypted, byte[] rawKey) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param
     * @return 密钥数据
     */
    private static byte[] getRawKey(byte[] seed) {
        byte[] rawKey = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);
            // AES加密数据块分组长度必须为128比特，密钥长度可以是128比特、192比特、256比特中的任意一个
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            rawKey = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
        }
        return rawKey;
    }

  /*  public String b(String paramString1, String paramString2)
    {
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] arrayOfByte = new byte[16];
        paramString2 = paramString2.getBytes("UTF-8");
        int j = paramString2.length;
        int i = j;
        if (j > arrayOfByte.length) {
            i = arrayOfByte.length;
        }
        System.arraycopy(paramString2, 0, arrayOfByte, 0, i);
        try {
            localCipher.init(2, new SecretKeySpec(arrayOfByte, "AES"), new IvParameterSpec(arrayOfByte));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return new String(localCipher.doFinal(Base64.decode(paramString1, 0)), "UTF-8");
    }*/

}