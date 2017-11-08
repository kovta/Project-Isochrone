package com.kota.stratagem.webservice.processing;

import static io.restassured.RestAssured.given;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public abstract class AbstractEnpointTest {

	protected String basePath;

	@BeforeClass
	public void setup() {
		basePath = "http://localhost:8080/stratagem-svc/api";
	}

	public <T> T getResource(String locationHeader, Class<T> responseClass) {
		return given().when().get(locationHeader).then().statusCode(200).extract().as(responseClass);
	}

	@AfterClass
	public static void teardown() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}

}
