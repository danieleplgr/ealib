package com.ealib.crypto.aes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCripter {

	protected SecretKeySpec aesKey;
	protected SecretKey secretKey;

	protected Cipher decryptCipher;
	protected Cipher encryptCipher;

	public AESCripter(byte[] bytesKey) throws NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException {

		aesKey = new SecretKeySpec(bytesKey, "AES");
		decryptCipher = Cipher.getInstance("AES");
		decryptCipher.init(Cipher.DECRYPT_MODE, aesKey);

		encryptCipher = Cipher.getInstance("AES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);
	}

	public String encrypt(String clearString) throws Exception {
		byte[] cleartext = clearString.getBytes();
		byte[] ciphertext = encryptCipher.doFinal(cleartext);
		return new String(ciphertext);
	}

	public String decrypt(String cryptedString)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] deciphertext = decryptCipher.doFinal(cryptedString.getBytes());
		return new String(deciphertext);
	}

	public static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecretKey secretKey = keyGen.generateKey();
		return secretKey;
	}

}
