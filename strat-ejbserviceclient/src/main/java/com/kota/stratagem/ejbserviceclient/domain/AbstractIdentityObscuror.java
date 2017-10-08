package com.kota.stratagem.ejbserviceclient.domain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

@Deprecated
public abstract class AbstractIdentityObscuror {

	protected String obscuredId;

	private int length;

	public AbstractIdentityObscuror(Long id) {
		super();
		if (id != null) {
			try {
				this.obscuredId = this.generateObscuredId(id.toString());
			} catch (UnsupportedEncodingException | NoSuchProviderException e) {
				e.printStackTrace();
			}
			this.length = id.toString().length();
		}
	}

	public String getObscuredId() {
		return this.obscuredId;
	}

	protected String generateObscuredId(String id) throws UnsupportedEncodingException, NoSuchProviderException {
		String generatedId = null;
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-512");
			SecureRandom.getInstance("SHA1PRNG", "SUN");
			final SecureRandom saltRandomizer = SecureRandom.getInstance("SHA1PRNG", "SUN");
			final byte[] salt = new byte[64];
			saltRandomizer.nextBytes(salt);
			final String encodedSalt = new String(salt);
			md.update(encodedSalt.getBytes("UTF-8"));
			final byte[] bytes = md.digest(id.getBytes("UTF-8"));
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedId = sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new StringBuilder(generatedId).insert(generatedId.length() / 2, id.toString()).toString();
	}

	protected Long reverseObscuredId(String obscuredId) {
		return Long.parseLong(obscuredId.substring(obscuredId.length() / 2, this.length));
	}

}
