package com.kota.stratagem.security.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;

import org.mindrot.jbcrypt.BCrypt;

@PermitAll
@Stateless(mappedName = "ejb/PasswordManager")
public class PasswordManager implements PasswordCorrelationService, PasswordGenerationService {

	private String password;

	public PasswordManager() {

	}

	public PasswordManager(String password) {
		this.password = password;
	}

	@Override
	public String GenerateBCryptPassword(String plainPassword) {
		return BCrypt.hashpw(this.password, BCrypt.gensalt());
	}

	@Override
	public boolean BCryptCorrelation(String plainPassword, String encryptedPassword) {
		return BCrypt.checkpw(password, encryptedPassword);
	}

	@Override
	public String GenerateSHAPassword(String passwordToHash) throws UnsupportedEncodingException, NoSuchProviderException {
		String generatedPassword = null;
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-512");
			SecureRandom.getInstance("SHA1PRNG", "SUN");

			final SecureRandom saltRandomizer = SecureRandom.getInstance("SHA1PRNG", "SUN");
			final byte[] salt = new byte[64];
			saltRandomizer.nextBytes(salt);
			final String encodedSalt = new String(salt);

			md.update(encodedSalt.getBytes("UTF-8"));
			final byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			final StringBuilder sb = new StringBuilder();
			for(int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	@Override
	public boolean SHACorrelation(String plainPassword, String encryptedPassword) throws NoSuchProviderException {
		boolean correlates = false;
		try {
			correlates = this.GenerateSHAPassword(plainPassword).equals(encryptedPassword);
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return correlates;
	}

}
