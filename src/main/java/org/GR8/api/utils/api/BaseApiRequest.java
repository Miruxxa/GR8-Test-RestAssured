package org.GR8.api.utils.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public abstract class BaseApiRequest {
    public static Response baseGetRequest(final String endpoint) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .when().log().all()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response basePostRequest(final Object body, final String endpoint) {
        return RestAssured.given()
                .body(body)
                .header("Content-type", "application/json")
                .when().log().all()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response basePutRequest(final Object body, final String endpoint) {
        return RestAssured.given()
                .body(body)
                .header("Content-type", "application/json")
                .when().log().all()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response baseDeleteRequest(final String endpoint) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .when().log().all()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }
}
