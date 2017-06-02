package com.kota.stratagem.security.authenticity;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import com.kota.stratagem.security.encryption.PasswordManager;

public class LoginModule extends DatabaseServerLoginModule {

	private PasswordManager passwordManager;

	@Override
	protected boolean validatePassword(String inputPassword, String expectedPassword) {
		this.passwordManager = new PasswordManager();
		return this.passwordManager.BCryptCorrelation(inputPassword, expectedPassword);
	}

}