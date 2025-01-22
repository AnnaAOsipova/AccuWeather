package HW;


import Location.Location;
import Weather.Weather;

import io.qameta.allure.*;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование Forecast API")
public class OneHourForecastTest extends AccuweatherAbstractTest {

    @Test
    @DisplayName("Тест OneHourForecast - поиск прогноза погоды на 1 час")
    @Description("Данный тест предназначен для поиска прогноза погоды на 1 час по ID City")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.NORMAL)
    @Story("Вызов метода поиска прогноза погоды на 1 час по ID City")
    @Owner("Осипова Анна")
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

        Assertions.assertEquals(1, response.size());
    }
}


