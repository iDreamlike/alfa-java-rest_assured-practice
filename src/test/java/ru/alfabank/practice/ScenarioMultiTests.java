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


public class ScenarioMultiTests {

    // Реализуйте функциональность, которая указана в коллекции,
    // где каждый запрос будет отдельным и самодостаточным тестом
    private static RequestSpecification spec;

    @BeforeAll
    static void init() {
        spec = RestAssured.given()
                .baseUri("https://simple-books-api.glitch.me")
                .contentType(ContentType.JSON);
    }

    // ========= Check status ==========================================================================================
    @Test
    void checkStatus() {

        spec
                .get("/status")
                .then()
                .statusCode(200)
                .body("status", Matchers.equalTo("OK"));
    }

    // ========= Register client =======================================================================================
    @Test
    void registerClient() {

        ClientRequest request = new ClientRequest("Сергей",
                RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        ClientResponse clientResponse = spec
                .body(request)
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);
        }

    @Test
    void getListOfBooks() {
        // ========= Get list of books =================================================================================
        spec
                .get("/books")
                .then()
                .statusCode(200)
                .body("size()", equalTo(6));
    }

    // ========= Check book availability ===============================================================================
    @Test
    void checkBookAvailability () {
        String bookId = "1";
        spec
                .when()
                .log().all()
                .get("/books/" + bookId)
                .then()
                .statusCode(200)
                .body("available", Matchers.equalTo(true));
    }
    @Test
    void orderSelectedBook () {

    // ========= Order selected book ===============================================================================
        // Регистрируем клиента. Все думал как лучше, переиспользовать имеющийся код в другом тесте или
        // продублировать ради независимости теста. Решил дублировать.
        ClientRequest clientRequest = new ClientRequest("Сергей",
                RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        ClientResponse clientResponse = spec
                .body(clientRequest)
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);

        // Делаем заказ на зарегистрированного пользователя
        OrderRequest orderRequest = new OrderRequest(1, clientRequest.getClientName());
        Response orderResponse = spec
                .body(orderRequest)
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .when()
                .post("/orders");
        orderResponse.then()
                .statusCode(201)
                .body("created", Matchers.equalTo(true))
                .extract()
                .as(OrderResponse.class);
        orderResponse.prettyPrint();
    }

 //========= Get All book orders =======================================================================================
    @Test
    void getAllBookOrders () {
        // Регистрируем нового пользователя
        ClientRequest clientRequest = new ClientRequest("Сергей",
                RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        ClientResponse clientResponse = spec
                .body(clientRequest)
                .when()
                .log().all()
                .post("/api-clients")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);

        // Делаем заказ на зарегистрированного пользователя
        OrderRequest orderRequest = new OrderRequest(1, clientRequest.getClientName());
        Response orderResponse = spec
                .body(orderRequest)
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .when()
                .post("/orders");
        orderResponse.then()
                .statusCode(201)
                .body("created", Matchers.equalTo(true))
                .extract()
                .as(OrderResponse.class);
        orderResponse.prettyPrint();

        // Выводим список всех имеющихся заказов
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
    }

    // ========= Update order ======================================================================================
    @Test
    void updateOrder () {

        // Регистрируем нового пользователя
        ClientRequest clientRequest = new ClientRequest("Сергей",
                RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        ClientResponse clientResponse = spec
                .body(clientRequest)
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);

        // Делаем заказ на зарегистрированного пользователя
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
        System.out.println(orderResponse);

        // Обновляем заказ
        OrderRequest newOrderRequest = new OrderRequest(1, "Ууася");
        System.out.println(orderRequest);

        Response updatingOrder = spec
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .body(newOrderRequest)
                .when()
                .patch("/orders/" + orderResponse.getOrderId());
        updatingOrder.then()
                .log().all()
                .statusCode(204);
        updatingOrder.prettyPrint();
    }
    // ========= Check order updated ===============================================================================
    @Test
    void checkOrderUpdated () {

        // Регистрируем нового пользователя
        ClientRequest clientRequest = new ClientRequest("Сергей",
                RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        ClientResponse clientResponse = spec
                .body(clientRequest)
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);

        // Делаем заказ на зарегистрированного пользователя
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
        System.out.println(orderResponse);

        // Обновляем заказ
        OrderRequest newOrderRequest = new OrderRequest(1, "Ууася");
        System.out.println(orderRequest);

        Response updatingOrder = spec
                .auth()
                .oauth2(clientResponse.getAccessToken())
                .body(newOrderRequest)
                .when()
                .patch("/orders/" + orderResponse.getOrderId());
        updatingOrder.then()
                .log().all()
                .statusCode(204);
        updatingOrder.prettyPrint();

        // Проверяем обновление заказа
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
    }
    
    // ========= Delete order ======================================================================================
    @Test
    void deleteOrder() {

        // Регистрируем нового пользователя
        ClientRequest clientRequest = new ClientRequest("Сергей",
                RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        ClientResponse clientResponse = spec
                .body(clientRequest)
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);

        // Делаем заказ на зарегистрированного пользователя
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
        System.out.println(orderResponse);

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
