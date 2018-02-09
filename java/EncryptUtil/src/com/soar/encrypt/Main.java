package com.soar.encrypt;

import java.security.NoSuchAlgorithmException;

public class Main {
	public static void main(String[] args) {
		try {
			RSAUtils.getKeys();
			String data = "1";
			System.out.println("data:" + data);
			String encrypt = RSAUtils.encryptByPublicKey(data);
			System.out.println("encrypt:" + encrypt);
			String decrypt = RSAUtils.decryptByPrivateKey(encrypt);
			System.out.println("decrypt:" + decrypt);

			String key = AESUtils.generateKey();
			System.out.println("key:" + key);
			encrypt = AESUtils.encryptData(key, data);
			System.out.println("encrypt:" + encrypt);
			decrypt = AESUtils.decryptData(key, encrypt);
			System.out.println("decrypt:" + decrypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
