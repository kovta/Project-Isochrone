package com.kota.stratagem.security.encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchProviderException;

import javax.ejb.Local;

@Local
public interface PasswordGenerationService {

	String GenerateBCryptPassword(String plainPassword);

	String GenerateSHAPassword(String plainPassword) throws UnsupportedEncodingException, NoSuchProviderException;

}
