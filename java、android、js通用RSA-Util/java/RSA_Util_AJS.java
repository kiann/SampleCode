package com.soar.util;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

public class RSA_Util_AJS {
    private static final int MAX_ENCRYPT = 117;
    private static final int MAX_DECRYPT = 256;

    public static KeyPer createKeygen() {
        KeyPairGenerator keyPairGenerator;
        KeyPer key;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Key publicKey = keyPair.getPublic();
            Key privateKey = keyPair.getPrivate();

            key = new KeyPer();
            key.publicKey = getKeyString(publicKey);
            key.privateKey = getKeyString(privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return key;
    }

    private static String getKeyString(Key key) {
        return Hex.toHex(key.getEncoded());
    }

    private static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Hex.toByte(publicKey));
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    private static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Hex.toByte(privateKey));
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    private static byte[] charToByte(char ch) {
        List<Byte> bytes = new ArrayList<>();
        int c = ch;
        if (c >= 0x010000 && c <= 0x10FFFF) {
            bytes.add((byte) (((c >> 18) & 0x07) | 0xF0));
            bytes.add((byte) (((c >> 12) & 0x3F) | 0x80));
            bytes.add((byte) (((c >> 6) & 0x3F) | 0x80));
            bytes.add((byte) ((c & 0x3F) | 0x80));
        } else if (c >= 0x000800 && c <= 0x00FFFF) {
            bytes.add((byte) (((c >> 12) & 0x0F) | 0xE0));
            bytes.add((byte) (((c >> 6) & 0x3F) | 0x80));
            bytes.add((byte) ((c & 0x3F) | 0x80));
        } else if (c >= 0x000080 && c <= 0x0007FF) {
            bytes.add((byte) (((c >> 6) & 0x1F) | 0xC0));
            bytes.add((byte) ((c & 0x3F) | 0x80));
        } else {
            bytes.add((byte) (c & 0xFF));
        }
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }

    public static String encrypt(String text, String key) {
        try {
            PublicKey publicKey = getPublicKey(key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            StringBuffer result = new StringBuffer();
            int charCountAll = 0;
            StringBuffer chars = new StringBuffer();
            int c;
            while (charCountAll < text.length()) {
                c = charToByte(text.charAt(charCountAll)).length;
                if (chars.toString().getBytes().length + c > MAX_ENCRYPT) {
                    result.append(Hex.toHex(cipher.doFinal(chars.toString().getBytes())));
                    chars = new StringBuffer();
                } else if (chars.toString().getBytes().length + c == MAX_ENCRYPT) {
                    chars.append(text.charAt(charCountAll));
                    charCountAll++;
                    result.append(Hex.toHex(cipher.doFinal(chars.toString().getBytes())));
                    chars = new StringBuffer();
                } else {
                    chars.append(text.charAt(charCountAll));
                    charCountAll++;
                    if (charCountAll == text.length()) {
                        result.append(Hex.toHex(cipher.doFinal(chars.toString().getBytes())));
                    }
                }
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String text, String key) {
        try {
            PrivateKey privateKey = getPrivateKey(key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            StringBuffer result = new StringBuffer();
            int count = text.length() / MAX_DECRYPT;
            String split;
            for (int i = 0; i < count; i++) {
                split = text.substring(i * MAX_DECRYPT, (i + 1) * MAX_DECRYPT);
                result.append(new String(cipher.doFinal(Hex.toByte(split))));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class KeyPer {
        public String publicKey;
        public String privateKey;
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
