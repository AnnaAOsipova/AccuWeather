package HW;

import io.restassured.http.Method;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CitySearchTest extends AccuweatherAbstractTest{

   @Test
    public void CitySearch() {
       given()
               .queryParam("apikey", getApiKey())
               .pathParam("version", "v1")
               .queryParam("q", "Tel Aviv")
               .when()
               .request(Method.GET, getBaseUrl()+"locations/{version}/cities/search")
               .then()
               .statusCode(200);
   }
}
