/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright Headease B.V. (c) 2020.
 */

package nl.koppeltaal.springbootstarterjwks.util;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.lang.JoseException;

/**
 *
 */
public class KeyUtils {
	/**
	 * Parses a public key to an instance of {@link ECPublicKey}.
	 *
	 * @param publicKey the string representation of the public key.
	 * @return an instance of {@link ECPublicKey}.
	 */
	public static ECPublicKey getEcPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		return (ECPublicKey) keyFactory.generatePublic(
				new X509EncodedKeySpec(getEncodedKey(publicKey)));
	}

	public static ECPrivateKey getEcPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		return (ECPrivateKey) keyFactory.generatePrivate(
				new PKCS8EncodedKeySpec(getEncodedKey(privateKey)));
	}

	/**
	 * Parses a public key to an instance of {@link RSAPublicKey}.
	 *
	 * @param publicKey the string representation of the public key.
	 * @return an instance of {@link RSAPublicKey}.
	 */
	public static RSAPublicKey getRsaPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPublicKey) keyFactory.generatePublic(
				new X509EncodedKeySpec(getEncodedKey(publicKey)));
	}

	private static byte[] getEncodedKey(String key) {

		if(StringUtils.isNoneBlank(key)) {
			String cleanPublicKey = key.replace("\r", "");
			cleanPublicKey = cleanPublicKey.replace("\n", "");
			cleanPublicKey = cleanPublicKey.replaceAll("-----.*?-----", "");

			return Base64.decodeBase64(cleanPublicKey);
		}

		throw new IllegalArgumentException("Cannot generate encoded key, it's empty or null");
	}

	public static RSAPrivateKey getRsaPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) keyFactory.generatePrivate(
				new PKCS8EncodedKeySpec(getEncodedKey(privateKey)));
	}

	public static KeyPair getRsaKeyPair(String publicKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return new KeyPair(getRsaPublicKey(publicKey), getRsaPrivateKey(privateKey));

	}

	public static String getFingerPrint(PublicKey publicKey) throws JoseException {
		final JsonWebKey jsonWebKey = JsonWebKey.Factory.newJwk(publicKey);
		return jsonWebKey.calculateBase64urlEncodedThumbprint("MD5");
	}

	private static String toHexString(byte[] digest) {
		final StringBuilder rv = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
			if (i != 0) {
				rv.append(":");
			}
			int b = digest[i] & 0xff;
			String hex = Integer.toHexString(b);
			if (hex.length() == 1) rv.append("0");
			rv.append(hex);
		}
		return rv.toString();
	}

	public static String encodeKey(Key key) {
     return encodeBase64String(key.getEncoded());
 }

	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		return generateKeyPair(2048);
	}

	public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
		// Create a new generator
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		// Set the key size
		generator.initialize(keySize);
		// Generate a pair
		return generator.generateKeyPair();
	}
}
