import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPUTPATCHPOSTTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String POSTS = "posts";
    private static Faker faker;
    private long fakeUserId;
    private String fakeTitle;
    private String fakeBody;

    @BeforeAll
    public static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach(){
        fakeUserId = faker.number().randomNumber();
        fakeTitle = faker.lorem().sentence();
        fakeBody = faker.chuckNorris().fact();

    }


    @Test
    public void jsonplaceholderUpdatePostPUTTest(){

        JSONObject post = new JSONObject();
        post.put("userId", fakeUserId);
        post.put("title", fakeTitle);
        System.out.println(fakeTitle);
        post.put("body", fakeBody);
        System.out.println(fakeBody);

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .put(BASE_URL + "/" + POSTS + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(String.valueOf(fakeUserId), json.getString("userId"));
        assertEquals(fakeTitle, json.get("title"));
        assertEquals(fakeBody, json.get("body"));
    }

    @Test
    public void jsonplaceholderUpdatePostPATCHTest(){

        JSONObject postBody = new JSONObject();
        postBody.put("body", fakeBody);
        System.out.println(fakeBody);

        Response response = given()
                .contentType("application/json")
                .body(postBody.toString())
                .when()
                .patch(BASE_URL + "/" + POSTS + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeBody, json.get("body"));
    }
}
