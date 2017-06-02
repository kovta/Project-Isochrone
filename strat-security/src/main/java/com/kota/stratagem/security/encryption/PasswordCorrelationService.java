package com.kota.stratagem.security.encryption;

import java.security.NoSuchProviderException;

import javax.ejb.Local;

@Local
public interface PasswordCorrelationService {

	boolean BCryptCorrelation(String plainPassword, String encryptedPassword);

	boolean SHACorrelation(String plainPassword, String encryptedPassword) throws NoSuchProviderException;

}
