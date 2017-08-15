package com.kota.stratagem.security.authenticity;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import com.kota.stratagem.security.encryption.PasswordCorrelationService;
import com.kota.stratagem.security.encryption.PasswordManager;

public class LoginModule extends DatabaseServerLoginModule {

	private PasswordCorrelationService passwordManager;

	@Override
	protected boolean validatePassword(String inputPassword, String expectedPassword) {
		return this.getPasswordManager().BCryptCorrelation(inputPassword, expectedPassword);
	}

	public PasswordCorrelationService getPasswordManager() {
		if (this.passwordManager == null) {
			this.passwordManager = new PasswordManager();
		}
		return this.passwordManager;
	}

}