package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.kota.stratagem.webservice.core.AbstractEnpointTest;

public class TaskRestServiceTest extends AbstractEnpointTest {

	protected String path;

	public TaskRestServiceTest() {
		this.path = AbstractEnpointTest.basePath + "/TaskSet";
	}

	@Test
	public void getTaskById() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/1").then().statusCode(200);
	}

	@Test
	public void getTaskByInvalidId() {
		given().auth().basic(TESTUSER, TESTPASS).when().get(this.path + "/-1").then().statusCode(500);
	}

}