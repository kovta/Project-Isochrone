package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class ObjectiveRestServiceTest extends AbstractEnpointTest {

	public ObjectiveRestServiceTest() {
		RestAssured.basePath = basePath + "/ObjectiveSet";
	}

	@Test
	public void getListOfObjectives() {
		given().auth().basic("holly", "h123").when().get().then().statusCode(200);
	}

	@Test
	public void getObjectiveById() {
		given().auth().basic("holly", "h123").when().get("/1").then().statusCode(200);
	}

	@Test
	public void getObjectiveByInvalidId() {
		given().auth().basic("holly", "h123").when().get("/-1").then().statusCode(404);
	}

}
