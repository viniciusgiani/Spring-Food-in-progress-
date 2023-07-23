package com.spring.data;

import com.spring.data.domain.model.Kitchen;
import com.spring.data.domain.repository.KitchenRepository;
import com.spring.data.util.DatabaseCleaner;
import com.spring.data.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(Extension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenRegisterIT {

    private static final int KITCHEN_ID_NONEXISTENT = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private KitchenRepository kitchenRepository;

    private Kitchen americanKitchen;
    private int registeredKitchensQuantity;
    private String jsonChineseCorrectKitchen;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/kitchens";

        jsonChineseCorrectKitchen = ResourceUtils.getContentFromResource(
                "/json/correct/chinese-kitchen.json"
        );

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void returnStatus200_WhenQueryKitchens() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void returnCorrectQuantityOfKitchens_WhenQueryingKitchens() {
        given().accept(ContentType.JSON).when().get().then().body("", hasSize(registeredKitchensQuantity));
    }

    @Test
    public void returnStatus201_WhenRegisterKitchen() {
        given()
                .body(jsonChineseCorrectKitchen)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void returnStatus404_WhenQueryKitchen() {
        given()
                .pathParam("kitchenId", KITCHEN_ID_NONEXISTENT)
                .accept(ContentType.JSON)
                .when()
                .get("/{kitchenId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Kitchen thaiKitchen = new Kitchen();
        thaiKitchen.setName("Thai");
        kitchenRepository.save(thaiKitchen);

        americanKitchen = new Kitchen();
        americanKitchen.setName("American");
        kitchenRepository.save(americanKitchen);

        registeredKitchensQuantity = (int) kitchenRepository.count();
    }
}

