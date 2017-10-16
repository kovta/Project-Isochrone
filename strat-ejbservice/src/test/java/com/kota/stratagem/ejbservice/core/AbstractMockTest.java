package com.kota.stratagem.ejbservice.core;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

public abstract class AbstractMockTest {

	public static final String TECHNICAL_USER = "TECHINCAL_USER";

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

}
