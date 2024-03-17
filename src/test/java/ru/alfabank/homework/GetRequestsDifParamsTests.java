package ru.alfabank.homework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetRequestsDifParamsTests {

    private static RequestSpecification spec;

    @BeforeAll
    static void init() {
        spec = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com/");
    }

    @Test
    void getAllPostsBySpecificUser () {
        Post[] response = spec
                .when()
                .get("/user/1/posts")
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
    void getAllCommentsBySpecificPost () {
        Comment[] response = spec
                .when()
                .get("/comments?postId=1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("allcomments-schema.json"))
                .extract()
                .as(Comment[].class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();     // Вывожу красиво с помощью Gson
        String json = gson.toJson(response);
        System.out.println(json);
    }

    @Test
    void getAllAlbumsBySpecificUser () {
        Album[] response = spec
                .when()
                .get("/albums?userId=1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("allalbums-schema.json"))
                .extract()
                .as(Album[].class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();     // Вывожу красиво с помощью Gson
        String json = gson.toJson(response);
        System.out.println(json);
    }

    @Test
    void getAllPhotosBySpecificAlbum () {
        Photo[] response = spec
                .when()
                .get("/albums/1/photos")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("allphotos-schema.json"))
                .extract()
                .as(Photo[].class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();     // Вывожу красиво с помощью Gson
        String json = gson.toJson(response);
        System.out.println(json);
    }

    @Test
    void getAllTodosBySpecificUser () {
        Todo[] response = spec
                .when()
                .get("/user/1/todos")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("alltodos-schema.json"))
                .extract()
                .as(Todo[].class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();     // Вывожу красиво с помощью Gson
        String json = gson.toJson(response);
        System.out.println(json);
    }
}
