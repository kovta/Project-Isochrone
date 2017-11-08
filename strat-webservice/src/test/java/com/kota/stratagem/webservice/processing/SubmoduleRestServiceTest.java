package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.kota.stratagem.webservice.core.AbstractEnpointTest;

public class SubmoduleRestServiceTest extends AbstractEnpointTest {

	protected String path;

	public SubmoduleRestServiceTest() {
		this.path = AbstractEnpointTest.basePath + "/SubmoduleSet";
	}

	@Test
	public void getSubmoduleById() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/1").then().statusCode(200);
	}

	@Test
	public void getSubmoduleByInvalidId() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/-1").then().statusCode(500);
	}

}