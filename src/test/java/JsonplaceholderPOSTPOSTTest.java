import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class JsonplaceholderPOSTPOSTTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String POSTS = "posts";

    @Test
    public void jsonplaceholderPOSTNewPost(){

        JSONObject post = new JSONObject();
        post.put("userId", "1");
        post.put("title", "This is fake title");
        post.put("body", "This is fake body");

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .post(BASE_URL + "/" + POSTS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("1", json.getString("userId"));
        assertEquals("This is fake title", json.get("title"));
        assertEquals("This is fake body", json.get("body"));

    }
}
