package ru.alfabank.practice3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.alfabank.practice.BookRequest;
import ru.alfabank.practice.ClientRequest;
import static org.junit.jupiter.api.Assertions.assertAll;


public class FifthTaskTest {

    // Возьмите тестовый сценарий из PostRequestTests.
    // В этом тесте получите ответ и сохраните его в переменную.
    // Проверьте статус код и ответ через Assertions.метод
    // Напишите второй такой же метод, но в проверках используйте assertAll()

    @Test
    void shouldRegisterClientAssertions() {
        String randomMail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        ClientRequest request = new ClientRequest("Сергей", randomMail);

        BookRequest req = new BookRequest(3, "Сергей");
        Response response = RestAssured.given()
                .baseUri("https://simple-books-api.glitch.me")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .body(request)
                .post("/api-clients");

        Assertions.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    void shouldRegisterClientAssertAll() {
        String randomMail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        ClientRequest request = new ClientRequest("Сергей", randomMail);

        BookRequest req = new BookRequest(3, "Сергей");
        Response response = RestAssured.given()
                .baseUri("https://simple-books-api.glitch.me")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .body(request)
                .post("/api-clients");

        assertAll("Проверка статус кода",
                () -> Assertions.assertEquals(201, response.getStatusCode(), "Неверный статус код")
        );
    }

}
