package ru.alfabank;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class PostRequestsTests {

    @Test
    void shouldRegisterClient() {
        // POST запрос на https://simple-books-api.glitch.me/api-clients
        // С телом запроса - для этого создайте класс ClientRequest и передавайте его в запрос
        // Структура запроса - given() - задать baseUrl и body, when(), then()
        // {
        //    "clientName": "Сергей",
        //    "clientEmail": "Какая-то почта"
        // }
        // Проверить статус код 201
        // Забрать из ответа accessToken - создать класс ClientResponse и вернуть его в ответе через метод extract()
        // Распечатать token
        //--------------------------
        // Добавьте еще один запрос на https://simple-books-api.glitch.me/orders
        // Нужно указать авторизацию Bearer с токеном который получили ранее
        // Нужно попробовать 2 варианта указания токенов через auth() вместе с oauth2()
        // и через указание заголовка Authorization
        // На вход нужно передать:
        //        {
        //            "bookId": 6,
        //            "customerName": "Сергей"
        //        }
        //        Также создайте классы реквеста и респона для этого
    }
}
