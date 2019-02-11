package org.allesoft.kafka

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.testng.annotations.Test


/*
    Author: Kirill Abramovich
*/

class MapTestIT {

    @Test
    fun mapTest() {
        RestAssured.baseURI = "http://185.207.207.201:8080/";

        given ()
                .contentType("application/x-www-form-urlencoded")
                .body("width=10&height=10")
                .expect()
                .body("status", equalTo<String>("OK"))
                .`when`()
                .post("/createMap")
                .body()
    }

    @Test
    fun configure() {
        RestAssured.baseURI = "http://185.207.207.201:8080/";
        given ()
                .contentType("application/x-www-form-urlencoded")
                .expect()
                .body("status", equalTo<String>("OK"))
                .`when`()
                .post("/configure")
                .body()
    }

}