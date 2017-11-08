package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.kota.stratagem.webservice.core.AbstractEnpointTest;

public class ProjectRestServiceTest extends AbstractEnpointTest {

	protected String path;

	public ProjectRestServiceTest() {
		this.path = AbstractEnpointTest.basePath + "/ProjectSet";
	}

	@Test
	public void getListOfProject() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path).then().statusCode(200);
	}

	@Test
	public void getProjectById() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/1").then().statusCode(200);
	}

	@Test
	public void getProjectByInvalidId() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/-1").then().statusCode(500);
	}

}
