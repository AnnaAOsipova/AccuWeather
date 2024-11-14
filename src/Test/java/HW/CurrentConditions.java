package HW;
import Weather.Weather;
import Location.Location;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CurrentConditions extends AccuweatherAbstractTest {

    @Test
    void CurrentConditionsTest(){
        given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "v1")
                .pathParam("ID", "215854")
                .when()
                .request(Method.GET, getBaseUrl()+"currentconditions/{version}/{ID}")
                .then()
                .statusCode(200);
    }
}
