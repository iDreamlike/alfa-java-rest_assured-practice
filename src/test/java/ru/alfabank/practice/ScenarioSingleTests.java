package ru.alfabank.practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;



public class ScenarioSingleTests {

    // Реализуйте функциональность, которая указана в коллекции в одном тесте

    private static RequestSpecification spec;

    @BeforeAll
    static void init() {
        spec = RestAssured.given()
                .baseUri("https://simple-books-api.glitch.me")
                .contentType(ContentType.JSON);
    }

    @Test
    void scenarioTest() {
        // ========= Check status ======================================================================================
        spec
                .get("/status")
                .then()
                .statusCode(200)
                .body("status", Matchers.equalTo("OK"));

        // ========= Register client ===================================================================================
        ClientRequest request = new ClientRequest("Сергей",
                RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        ClientResponse clientResponse = spec
                .body(request)
                .when()
                .post("/api-clients")
                .then()
//                .log().all()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);
//        System.out.println(clientResponse);

        // ========= Get list of books =================================================================================
        spec
                .get("/books")
                .then()
                .statusCode(200)
                .body("size()", equalTo(6));

        // ========= Check book availability ===========================================================================
        String bookId = "1";
        spec
//                .queryParam("name", "sergei")
//                .queryParam("la", "123")
                .when()
                .get("/books/" + bookId)
                .then()
//                .log().all()
                .statusCode(200)
                .body("available", Matchers.equalTo(true));

        // ========= Order selected book ===============================================================================
        ClientRequest clientRequest = new ClientRequest("Сергей", "mail1@mail.ru");
        OrderRequest orderRequest = new OrderRequest(1, clientRequest.getClientName());
        OrderResponse orderResponse = spec
                .body(orderRequest)
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .when()
                .post("/orders")
                .then()
                .statusCode(201)
                .body("created", Matchers.equalTo(true))
                .extract()
                .as(OrderResponse.class);
        System.out.println(clientResponse);

        // ========= Get All book orders ===============================================================================
        Response allBookOrders = spec
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .when()
                .get("/orders");
        allBookOrders.then()
                .statusCode(200)
                .assertThat()
                .body("", Matchers.hasSize(1))
                .body("[0].bookId", Matchers.equalTo(orderRequest.getBookId()))
                .body("[0].customerName", Matchers.equalTo(clientRequest.getClientName()));
        allBookOrders.prettyPrint();

        // ========= Update order ======================================================================================
        OrderRequest newOrderRequest = new OrderRequest(1, "Ууася");
        System.out.println(orderRequest);

        Response updatingOrder = spec
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .body(newOrderRequest)
                .when()
                .patch("/orders/" + orderResponse.getOrderId());
        updatingOrder.then()
                .statusCode(204);

        // ========= Check order updated ===============================================================================
        Response checkingUpdatedOrder = spec
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .pathParam("orderId", orderResponse.getOrderId())
                .when()
                .get("/orders/{orderId}");
        checkingUpdatedOrder.then()
                .statusCode(200)
                .body("customerName", Matchers.equalTo(newOrderRequest.getCustomerName()));
        checkingUpdatedOrder.prettyPrint();

        // ========= Delete order ======================================================================================
        Response deletingOrder = spec
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .pathParam("orderId", orderResponse.getOrderId())
                .when()
                .delete("/orders/{orderId}");
        deletingOrder.then()
                .statusCode(204)
                .log().all();
    }
}
