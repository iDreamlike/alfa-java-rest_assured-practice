package ru.alfabank.homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class GetRequestsTests {

    private static RequestSpecification spec;

    @BeforeAll
    static void init() {
        spec = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com/");
    }

    @Test
    void getAllUsers() {
        User[] response = spec
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("allusers-schema.json"))
                .extract()
                .as(User[].class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();     // Вывожу красиво с помощью Gson
        String json = gson.toJson(response);
        System.out.println(json);
    }

    @Test
    void getSpecificUser() {
        Response response = spec
                .when()
                .get("/users/1");
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("user-schema.json"))
                .extract()
                .as(User.class);
        response.prettyPrint();
    }

    @Test
    void getAllPosts() {
        Post[] response = spec
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("allposts-schema.json"))
                .extract()
                .as(Post[].class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();     // Вывожу красиво с помощью Gson
        String json = gson.toJson(response);
        System.out.println(json);
    }

    @Test
    void getSpecificPost() {
        Response response = spec
                .when()
                .get("/posts/1");
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("post-schema.json"))
                .extract()
                .as(Post.class);
        response.prettyPrint();
    }
    @Test
    void getAllComments() {
        try {                                                   // IDEA порекомендовала обработать исключение
            Comment[] response = spec                           // при работе с Jackson
                    .when()
                    .get("/comments")
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("allcomments-schema.json"))
                    .extract()
                    .as(Comment[].class);

            ObjectMapper objectMapper = new ObjectMapper();     // Вывожу красиво с помощью Jackson
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            System.out.println(json);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
