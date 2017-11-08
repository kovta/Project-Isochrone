package com.kota.stratagem.webservice.core;

import static io.restassured.RestAssured.given;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public abstract class AbstractEnpointTest {

	protected static String basePath = "http://localhost:8080/stratagem-svc/api";

	protected static String TESTUSER = "holly";
	protected static String TESTPASS = "h123";

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/stratagem-svc/api";
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
