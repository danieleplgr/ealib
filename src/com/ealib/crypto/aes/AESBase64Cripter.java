package com.ealib.crypto.aes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.kobjects.base64.Base64;

public class AESBase64Cripter extends AESCripter {

	public AESBase64Cripter(byte[] secretKey) throws NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException {
		super(secretKey);
	}

	public String encrypt(String clearString) throws Exception {
		byte[] cleartext = clearString.getBytes();
		byte[] ciphertext = encryptCipher.doFinal(cleartext);
		String encode = Base64.encode(ciphertext);
		return new String(encode);
	}

	public String decrypt(String cryptedBase64String)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] decode = Base64.decode(cryptedBase64String);
		byte[] deciphertext = decryptCipher.doFinal(decode);
		String string = new String(deciphertext);
		return string;
	}

}
