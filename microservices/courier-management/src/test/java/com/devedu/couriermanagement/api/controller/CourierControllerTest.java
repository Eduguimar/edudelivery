package com.devedu.couriermanagement.api.controller;

import com.devedu.couriermanagement.domain.model.Courier;
import com.devedu.couriermanagement.domain.repository.CourierRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourierControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CourierRepository courierRepository;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/couriers";
    }

    @Test
    public void shouldReturn201() {
        String requestBody = """
                {
                    "name": "João Silva",
                    "phone": "11987676545"
                }
            """;

        RestAssured
            .given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("João Silva"));

    }

    @Test
    public void shouldReturn200() {
        UUID courierId = courierRepository.saveAndFlush(
                Courier.brandNew("Maria Souza", "11989987482")
        ).getId();

        RestAssured
            .given()
                .accept(ContentType.JSON)
            .when()
                .get("/{courierId}", courierId)
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(courierId.toString()))
                .body("name", Matchers.equalTo("Maria Souza"))
                .body("phone", Matchers.equalTo("11989987482"));
    }

}