package ru.alfabank.practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ScenarioMultiTests {

    // === РЕГИСТРАЦИЯ КЛИЕНТА ===
    ClientResponse registerClient(String name, String email) {

        ClientRequest request = new ClientRequest(name, email);
        return spec
                .body(request)
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract()
                .as(ClientResponse.class);
    }

    // === ЗАКАЗ ВЫБРАННОЙ КНИГИ ===
    OrderResponse orderSelectedBook (int bookId, String bookName, String token) {
        OrderRequest orderRequest = new OrderRequest(bookId, bookName);
        return spec
                .body(orderRequest)
                .auth()
                .oauth2(token)
                .when()
                .post("/orders")
                .then()
                .statusCode(201)
                .body("created", Matchers.equalTo(true))
                .extract()
                .as(OrderResponse.class);
    }



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
    void shouldRegister() {
        ClientResponse response = registerClient("Сергей", RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
        String token = response.getAccessToken();
        System.out.println("Регистрация успешна, получили токен: " + token);
    }

    // ========= Get list of books =====================================================================================
    @Test
    void getListOfBooks() {
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

    // ========= Order selected book ===================================================================================
    @Test
    void shouldOrderBook () {
        // Регистрируем нового пользователя
        String clientName = "Сергей";
        String clientEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        ClientResponse respFromRegistration = registerClient(clientName, clientEmail);
        String token = respFromRegistration.getAccessToken();
        System.out.println("Регистрация успешна, получили токен: " + token);

        // Делаем заказ на созданного пользователя
        int bookId = 1;
        OrderResponse respFromOrder = orderSelectedBook(bookId, clientName, token);
        assertThat(respFromOrder.getCreated(), equalTo(Boolean.TRUE));
        System.out.println("Заказ оформлен. orderID: " + respFromOrder.getOrderId());
    }

 //========= Get All book orders =======================================================================================
    @Test
    void getAllBookOrders () {
        // Регистрируем нового пользователя
        String clientName = "Сергей";
        String clientEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        ClientResponse respFromRegistration = registerClient(clientName, clientEmail);
        String token = respFromRegistration.getAccessToken();
        System.out.println("Регистрация успешна, получили токен: " + token);

        // Делаем заказ на созданного пользователя
        int bookId = 1;
        OrderResponse respFromOrder = orderSelectedBook(bookId, clientName, token);
        assertThat(respFromOrder.getCreated(), equalTo(Boolean.TRUE));
        System.out.println("Заказ оформлен. orderID: " + respFromOrder.getOrderId());

        // Выводим список всех имеющихся заказов
        Response allBookOrders = spec
                .auth()
                .oauth2(token)
                .when()
                .get("/orders");
        allBookOrders.then()
                .statusCode(200)
                .assertThat()
                .body("", Matchers.hasSize(1))
                .body("[0].bookId", Matchers.equalTo(bookId))
                .body("[0].customerName", Matchers.equalTo(clientName));
        allBookOrders.prettyPrint();
    }

    // ========= Update order ======================================================================================
    @Test
    void updateOrder () {

        // Регистрируем нового пользователя
        String clientName = "Сергей";
        String clientEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        ClientResponse respFromRegistration = registerClient(clientName, clientEmail);
        String token = respFromRegistration.getAccessToken();
        System.out.println("Регистрация успешна, получили токен: " + token);

        // Делаем заказ на созданного пользователя
        int bookId = 1;
        OrderResponse respFromOrder = orderSelectedBook(bookId, clientName, token);
        assertThat(respFromOrder.getCreated(), equalTo(Boolean.TRUE));
        System.out.println("Заказ оформлен. orderID: " + respFromOrder.getOrderId());

        // Обновляем заказ
        OrderRequest newOrderRequest = new OrderRequest(bookId, "Дауай Ууасся!");
        Response updatingOrder = spec
                .auth()
                .oauth2(token)
                .body(newOrderRequest)
                .when()
                .patch("/orders/" + respFromOrder.getOrderId());
        updatingOrder.then()
                .statusCode(204);
    }

    // ========= Check order updated ===============================================================================
    @Test
    void checkOrderUpdated () {

        // Регистрируем нового пользователя
        String clientName = "Сергей";
        String clientEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        ClientResponse respFromRegistration = registerClient(clientName, clientEmail);
        String token = respFromRegistration.getAccessToken();
        System.out.println("Регистрация успешна, получили токен: " + token);

        // Делаем заказ на созданного пользователя
        int bookId = 1;
        OrderResponse respFromOrder = orderSelectedBook(bookId, clientName, token);
        assertThat(respFromOrder.getCreated(), equalTo(Boolean.TRUE));
        System.out.println("Заказ оформлен. orderID: " + respFromOrder.getOrderId());

        // Обновляем заказ
        OrderRequest newOrderRequest = new OrderRequest(bookId, "Дауай Ууасся!");
        Response updatingOrder = spec
                .auth()
                .oauth2(token)
                .body(newOrderRequest)
                .when()
                .patch("/orders/" + respFromOrder.getOrderId());
        updatingOrder.then()
                .statusCode(204);

        // Проверяем обновление заказа
        Response checkingUpdatedOrder = spec
                .auth()
                .oauth2(token)
                .pathParam("orderId", respFromOrder.getOrderId())
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
        String clientName = "Сергей";
        String clientEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        ClientResponse respFromRegistration = registerClient(clientName, clientEmail);
        String token = respFromRegistration.getAccessToken();
        System.out.println("Регистрация успешна, получили токен: " + token);

        // Делаем заказ на созданного пользователя
        int bookId = 1;
        OrderResponse respFromOrder = orderSelectedBook(bookId, clientName, token);
        assertThat(respFromOrder.getCreated(), equalTo(Boolean.TRUE));
        System.out.println("Заказ оформлен. orderID: " + respFromOrder.getOrderId());

        // Удаляем заказ
        Response deletingOrder = spec
                .auth()
                .oauth2(token)
                .pathParam("orderId", respFromOrder.getOrderId())
                .when()
                .delete("/orders/{orderId}");
        deletingOrder.then()
                .statusCode(204)
                .log().all();
    }
}
