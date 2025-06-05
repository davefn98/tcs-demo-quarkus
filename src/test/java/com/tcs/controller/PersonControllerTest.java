package com.tcs.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class PersonControllerTest {

    @Test
    void testGetAllPersonsEndpoint() {
        RestAssured.given()
            .when().get("/persons")
            .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("data", not(empty()));
    }

    @Test
    void testGetPersonByIdEndpoint_found() {
        RestAssured.given()
            .when().get("/persons/1")
            .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("data[0].id", equalTo(1));
    }

    @Test
    void testGetPersonByIdEndpoint_notFound() {
        RestAssured.given()
            .when().get("/persons/99999")
            .then()
            .statusCode(404);
    }
}