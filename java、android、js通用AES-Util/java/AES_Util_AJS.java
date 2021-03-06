package com.soar.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lsoar on 2018/2/25.
 * 适用于Android、Java和js的AES加密工具，使用CBC模式
 * 加密内容前后不能有空格等分隔符
 */
public class AES_Util_AJS {
    private static char[] charPool = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final String VIPARA = "0123456789012345";

    public static String createKeygen() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            buffer.append(charPool[(int) (Math.random() * 36)]);
        }
        return buffer.toString();
    }

    public static String encrypt(String data, String key) {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(VIPARA.getBytes("UTF-8"));

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Hex.toHex(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String data, String key) {
        try {
            byte[] encrypted1 = Hex.toByte(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(VIPARA.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class Hex {
        public static byte[] toByte(String hex) {
            int len = (hex.length() / 2);
            byte[] result = new byte[len];
            char[] achar = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int pos = i * 2;
                result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
            }
            return result;
        }

        public static String toHex(byte[] bytes) {
            StringBuffer sb = new StringBuffer(bytes.length);
            String sTemp;
            for (int i = 0; i < bytes.length; i++) {
                sTemp = Integer.toHexString(0xFF & bytes[i]);
                if (sTemp.length() < 2)
                    sb.append(0);
                sb.append(sTemp.toUpperCase());
            }
            return sb.toString();
        }

        private static byte toByte(char c) {
            byte b = (byte) "0123456789ABCDEF".indexOf(c);
            return b;
        }
    }
}
