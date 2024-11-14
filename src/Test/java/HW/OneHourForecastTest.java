package HW;


import Location.Location;
import Weather.Weather;

import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OneHourForecastTest extends AccuweatherAbstractTest {
    @Test
    public void OneHourForecast() {
        List<Location> response = (List<Location>) given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "v1")
                .pathParam("ID", "215854")
                .when()
                .request(Method.GET, getBaseUrl() + "forecasts/{version}/hourly/1hour/{ID}")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertEquals(10, response.size());
        Assertions.assertEquals("Tel Aviv", response.get(0).getLocalizedName());
    }
}


