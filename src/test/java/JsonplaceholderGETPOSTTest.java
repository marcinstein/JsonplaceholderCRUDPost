import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class JsonplaceholderGETPOSTTest {

   private final String BASE_URL = "https://jsonplaceholder.typicode.com";
   private final String POSTS = "posts";

    @Test
    public void jsonplaceholderReadAllPosts(){
        Response response = given()
                .when()
                .get(BASE_URL + "/" + POSTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> titles = json.getList("title");
      //  titles.stream()
      //         .forEach(System.out::println);

        assertEquals(100,titles.size());
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", titles.get(0));

       // System.out.println(response.asString());
    }

    @Test
    public void jsonplaceholderReadOnePost(){
        Response response = given()
                .when()
                .get(BASE_URL + "/" + POSTS + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        System.out.println(response.asString());

        JsonPath json = response.jsonPath();
        assertEquals("1", json.getString("userId"));
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", json.get("title"));
        assertEquals("quia et suscipit" + "\n" +
                "suscipit recusandae consequuntur expedita et cum" +
                "\n" + "reprehenderit molestiae ut ut quas totam" +
                "\n" + "nostrum rerum est autem sunt rem eveniet architecto", json.get("body"));

    }

    //Query Parameters
    @Test
    public void jsonplaceholderReadOnePostQueryParam(){
        Response response = given()
                .queryParam("title", "qui est esse")
                .when()
                .get(BASE_URL + "/" + POSTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        System.out.println(response.asString());

        JsonPath json = response.jsonPath();
        assertEquals("1", String.valueOf(json.getList("userId").get(0)));
        assertEquals("qui est esse", json.getList("title").get(0));
        assertEquals("est rerum tempore vitae" + "\n" +
                "sequi sint nihil reprehenderit dolor beatae ea dolores neque" +
                "\n" + "fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis" +
                "\n" + "qui aperiam non debitis possimus qui neque nisi nulla", json.getList("body").get(0));

    }

    //Path Variables
    @Test
    public void jsonplaceholderReadOnePostPathVariable() {
        Response response = given()
                .pathParam("postId", 1)
                .when()
                .get(BASE_URL + "/" + POSTS + "/{postId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        System.out.println(response.asString());

        JsonPath json = response.jsonPath();
        assertEquals("1", json.getString("userId"));
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", json.get("title"));
        assertEquals("quia et suscipit" + "\n" +
                "suscipit recusandae consequuntur expedita et cum" +
                "\n" + "reprehenderit molestiae ut ut quas totam" +
                "\n" + "nostrum rerum est autem sunt rem eveniet architecto", json.get("body"));
    }

}
