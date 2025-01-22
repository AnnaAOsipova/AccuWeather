package HW;

import Weather.Weather;

import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;


@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование Indices API")
public class TenDaysSpecificIndexTest extends AccuweatherAbstractTest{
    @Test
    @DisplayName("Тест getTenDaysSpecificIndex_shouldReturn_401 (негативный) - поиск погоды за 10 дней")
    @Description("Данный тест предназначен для поиска погоды за 10 дней")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Вызов метода поиска погоды за 10 дней")
    @Owner("Осипова Анна")
    void getTenDaysSpecificIndex_shouldReturn_401() {

        Weather response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"forecasts/v1/daily/10day/215854")
                .then()
                .statusCode(401)
                .time(Matchers.lessThan(2000L))
                .extract()
                .response()
                .body().as(Weather.class);

    }
}
