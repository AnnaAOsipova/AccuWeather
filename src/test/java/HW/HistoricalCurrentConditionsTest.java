package HW;
import Location.Location;

import io.qameta.allure.*;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование Current Conditions API")
public class HistoricalCurrentConditionsTest extends AccuweatherAbstractTest{

    @Test
    @DisplayName("Тест HistoricalCurrentConditionsTest - поиск погоды за прошедшие сутки")
    @Description("Данный тест предназначен для поиска погоды за прошедшие сутки по ID City")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Вызов метода поиска погоды за прошедшие сутки по ID City")
    @Owner("Осипова Анна")
    public void HistoricalCurrentConditionsTest(){
        List<Location> response = (List<Location>) given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "v1")
                .pathParam("ID", "215854")
                .when()
                .request(Method.GET, getBaseUrl() + "currentconditions/{version}/{ID}/historical/24")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertEquals(24, response.size());
    }
}
