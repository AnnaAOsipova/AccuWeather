package HW;

import Weather.Weather;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class TenDaysSpecificIndex extends AccuweatherAbstractTest{
    @Test
    void getTenDaysSpecificIndex_shouldReturn() {

        Weather response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/5day/215854")
                .then()
                .statusCode(401)
                .time(Matchers.lessThan(2000L))
                .extract()
                .response()
                .body().as(Weather.class);

       // Assertions.assertEquals(5,response.getDailyForecasts().size());
    }
}
