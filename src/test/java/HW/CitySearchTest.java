package HW;

import io.qameta.allure.*;
import io.restassured.http.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование Locations API")
public class CitySearchTest extends AccuweatherAbstractTest{

   @Test
   @DisplayName("Тест CitySearch - поиск объекта City")
   @Description("Данный тест предназначен для поиска ID City по названию")
   @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
   @Severity(SeverityLevel.BLOCKER)
   @Story("Вызов метода поиска ID города по названию")
   @Owner("Осипова Анна")
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
