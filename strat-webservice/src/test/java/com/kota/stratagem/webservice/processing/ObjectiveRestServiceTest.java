package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class ObjectiveRestServiceTest extends AbstractEnpointTest {

	protected String path;

	public ObjectiveRestServiceTest() {
		this.path = this.basePath + "/ObjectiveSet";
	}

	@Test
	public void getListOfObjectives() {
		given().auth().basic("holly", "h123").when().get(path).then().statusCode(200);
	}

	@Test
	public void getObjectiveById() {
		given().auth().basic("holly", "h123").when().get(path + "/1").then().statusCode(200);
	}

	@Test
	public void getObjectiveByInvalidId() {
		given().auth().basic("holly", "h123").when().get(path + "/-1").then().statusCode(404);
	}

}
