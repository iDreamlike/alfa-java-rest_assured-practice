package ru.alfabank.homework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostRequestsTests {

    private static RequestSpecification spec;

    @BeforeAll
    static void init() {
        spec = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .contentType(ContentType.JSON);
    }

    @Test
    void createNewUser() {
        Geo geo = new Geo("lat1", "lng1");
        Address address = new Address("Улица", "Район", "Город", "Индекс", geo);
        Company company = new Company("Название компании", "Слоган", "bs1");
        User requestUser = new User("Вася Пупкин", "Uasya", "mail1@mail.ru", address,
                "12312313", "сайт", company);

        User responseUser = spec
//                .log().all()
                .body(requestUser)
                .when()
//                .log().all()
                .post("/users")
                .then()
//                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("user-schema.json"))
                .extract()
                .as(User.class);
        System.out.println("==================");
        System.out.println(responseUser);
    }

    @Test
    void createNewPost() {
        Post requestPost = new Post(1, 101, "Заголовок", "Текст поста");

        Post responsePost = spec
                .body(requestPost)
                .when()
                .post("/posts")
                .then()
//                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("post-schema.json"))
                .extract()
                .as(Post.class);
        System.out.println(responsePost);
    }

    @Test
    void createNewComment() {
        Comment requestComment = new Comment(1, 2, "Название", "mail1@mail.ru", "Текст");

        Comment responseComment = spec
                .body(requestComment)
                .when()
                .post("/comments")
                .then()
//                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("comment-schema.json"))
                .extract()
                .as(Comment.class);
        System.out.println(responseComment);
    }

    @Test
    void createNewAlbum() {
        Album requestAlbum = new Album(1, 2, "Название");

        Album responseAlbum = spec
                .body(requestAlbum)
                .when()
                .post("/albums")
                .then()
//                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("album-schema.json"))
                .extract()
                .as(Album.class);
    }

    @Test
    void createNewTodo() {
        Todo requestTodo = new Todo(1,2,"Название", true);

        Todo responseTodo = spec
                .body(requestTodo)
                .when()
                .post("/todos")
                .then()
//                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("todo-schema.json"))
                .extract()
                .as(Todo.class);

    }

}
