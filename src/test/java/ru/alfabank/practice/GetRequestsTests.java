package ru.alfabank.practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetRequestsTests {

    // Делаем после выполнения третьего задания.
    // Создайте поле класса с типом RequestSpecification и в методе init задайте baseUri для всех запросов.
    // После этого поправьте все тесты на использование RequestSpecification

    private static RequestSpecification spec;

    @BeforeAll
    static void init() {
        spec = RestAssured.given()
                .baseUri("https://simple-books-api.glitch.me");
    }

    @Test
    void heathCheckTest() {
        // GET запрос на https://simple-books-api.glitch.me/status
        // Проверить статус 200
        spec
                .when()
                .get("/status")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturnBooksTest() {
        // GET запрос на https://simple-books-api.glitch.me/books
        // Проверить статус.
        // Проверить что в теле в первой книге name равно The Russian
        RestAssured
                .get("https://simple-books-api.glitch.me/books")
                .then()
                .statusCode(200)
//                .body("name[0]", Matchers.equalTo("The Russian"));
                .body("[0].name", Matchers.equalTo("The Russian"));
    }

    @Test
    void shouldReturnBooksTestAndPrint() {
        // GET запрос на https://simple-books-api.glitch.me/books
        // Сохранить ответ в объект класса Response.
        // Проверить статус == 200
        // Вывести 3 раза ответ через prettyPrint(), через возврат как asString() и как prettyString()
        Response response = spec
                .when()
                .get("/books");
        response.then()
                .statusCode(200);
        response.prettyPrint();
//        System.out.println(response.prettyPrint());
        System.out.println(response.asString());
        System.out.println(response.asPrettyString());
    }

}
