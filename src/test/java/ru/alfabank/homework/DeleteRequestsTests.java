package ru.alfabank.homework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DeleteRequestsTests {

    private static RequestSpecification spec;

    @BeforeAll
    static void init() {
        spec = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .contentType(ContentType.JSON);
    }

    @Test
    void deleteUser() {
        spec
                .delete("/users/1")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void deletePost() {
        spec
                .delete("/posts/1")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void deleteComment() {
        spec
                .delete("/comments/1")
                .then()
                .log().all()
                .statusCode(200);
    }
}
