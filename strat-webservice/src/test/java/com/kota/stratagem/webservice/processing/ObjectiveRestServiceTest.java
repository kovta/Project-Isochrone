package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.kota.stratagem.webservice.core.AbstractEnpointTest;

public class ObjectiveRestServiceTest extends AbstractEnpointTest {

	protected String path;

	public ObjectiveRestServiceTest() {
		this.path = AbstractEnpointTest.basePath + "/ObjectiveSet";
	}

	@Test
	public void getListOfObjectives() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path).then().statusCode(200);
	}

	@Test
	public void getObjectiveById() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/1").then().statusCode(200);
	}

	@Test
	public void getObjectiveByInvalidId() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/-1").then().statusCode(500);
	}

}
