import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderGETUserOutOfPLTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";

    @Test
    public void jsonplaceholderCheckUserFromPl() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();


        JsonPath json = response.jsonPath();


        List<String> emails = json.getList("email");

        List<String> b = emails.stream()
                .filter(email -> email.endsWith(".pl"))
                .collect(Collectors.toList());
        assertTrue(b.isEmpty());

        Boolean k = false;
        for (int i=0; i< emails.size(); i++) {
            if (emails.get(i).endsWith(".pl")) {
                k = true;
            }
        }
        //System.out.println(k);
        assertFalse(k);


        }



}
