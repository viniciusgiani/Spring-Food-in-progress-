package com.spring.data;

import static io.restassured.RestAssured.given;
import com.spring.data.domain.model.Kitchen;
import com.spring.data.domain.model.Restaurant;
import com.spring.data.domain.repository.KitchenRepository;
import com.spring.data.domain.repository.RestaurantRepository;
import com.spring.data.util.DatabaseCleaner;
import com.spring.data.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Extension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantRegisterIT {
    private static final String BUSINESS_RULE_VIOLATION_PROBLEM_TYPE = "Business rule violation";

    private static final String INVALID_DATA_PROBLEM_TITLE="Invalid data";

    private static final int RESTAURANT_ID_NONEXISTENT = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private String jsonCorrectRestaurant;
    private String jsonDeliveryFreeRestaurant;
    private String jsonWithoutKitchenRestaurant;
    private String jsonNonexistentRestaurantWithKitchen;

    private Restaurant burgerTopRestaurant;

    @Before("")
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        jsonCorrectRestaurant = ResourceUtils.getContentFromResource(
                "/json/correct/restaurant-new-york-barbecue.json"
        );

        jsonDeliveryFreeRestaurant = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-delivery-free.json"
        );

        jsonWithoutKitchenRestaurant = ResourceUtils.getContentFromResource(
                "json/incorrect/restaurant-new-york-barbecue-without-kitchen.json"
        );

        jsonNonexistentRestaurantWithKitchen = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-with-kitchen-nonexistent.json"
        );

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void returnStatus200_WhenQueryingRestaurants() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test void returnStatus201_WhenRegisterRestaurant() {
        given()
                .body(jsonCorrectRestaurant)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void returnStatus400_WhenRegisterRestaurantWithoutDeliveryFee() {
        given()
                .body(jsonDeliveryFreeRestaurant)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }

    @Test
    public void returnStatus400_WhenRegisterRestaurantWithoutKitchen() {
        given()
                .body(jsonWithoutKitchenRestaurant)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }

    @Test
    public void returnStatus400_WhenRegisterRestaurantWithKitchenNonexistent() {
        given()
                .body(jsonNonexistentRestaurantWithKitchen)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(BUSINESS_RULE_VIOLATION_PROBLEM_TYPE));
    }

    @Test
    public void returnCorrectResponseAndRequest_WhenQueryingExistentRestaurant() {
        given()
                .pathParam("id", burgerTopRestaurant.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(burgerTopRestaurant.getName()));
    }

    @Test
    public void returnStatus404_WhenQueryingNonexistentRestaurant() {
        given()
                .pathParam("id", RESTAURANT_ID_NONEXISTENT)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Kitchen brazilianKitchen = new Kitchen();
        brazilianKitchen.setName("Brazilian");
        kitchenRepository.save(brazilianKitchen);

        Kitchen americanKitchen = new Kitchen();
        americanKitchen.setName("American");
        kitchenRepository.save(americanKitchen);

        burgerTopRestaurant = new Restaurant();
        burgerTopRestaurant.setName("Burger Top");
        burgerTopRestaurant.setDeliveryFee(new BigDecimal(10));
        burgerTopRestaurant.setKitchen(americanKitchen);
        restaurantRepository.save(burgerTopRestaurant);

        Restaurant gauchoFoodRestaurant = new Restaurant();
        gauchoFoodRestaurant.setName("British Food");
        gauchoFoodRestaurant.setDeliveryFee(new BigDecimal(10));
        gauchoFoodRestaurant.setKitchen(brazilianKitchen);
        restaurantRepository.save(gauchoFoodRestaurant);
    }
}
