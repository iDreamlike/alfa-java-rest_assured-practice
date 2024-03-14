package ru.alfabank;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetRequestsTests {

    // Делаем после выполнения третьего задания
    // Создайте поле класса с типом RequestSpecification и в методе init задайте baseUri для всех запросов
    // После этого поправьте все тесты на использование RequestSpecification
    @BeforeAll
    static void init() {
    }

    @Test
    void heathCheckTest() {
        // GET запрос на https://simple-books-api.glitch.me/status
        // Проверить статус 200
    }

    @Test
    void shouldReturnBooksTest() {
        // GET запрос на https://simple-books-api.glitch.me/books
        // Проверить статус
        // Проверить что в теле в первой книге name равно The Russian
    }

    @Test
    void shouldReturnBooksTestAndPrint() {
        // GET запрос на https://simple-books-api.glitch.me/books
        // Сохранить ответ в объект класса Response
        // Проверить статус == 200
        // Вывести 3 раза ответ через prettyPrint(), через возврат как asString() и как prettyString()
    }

}
