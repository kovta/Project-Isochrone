package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.kota.stratagem.webservice.core.AbstractEnpointTest;

public class AppUserRestServiceTest extends AbstractEnpointTest {

	protected String path;

	public AppUserRestServiceTest() {
		this.path = AbstractEnpointTest.basePath + "/AppUserSet";
	}

	@Test
	public void getListOfAppUsers() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path).then().statusCode(200);
	}

}