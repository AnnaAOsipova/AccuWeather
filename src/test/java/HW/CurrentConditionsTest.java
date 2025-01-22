package HW;
import io.qameta.allure.*;
import io.restassured.http.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование Current Conditions API")
public class CurrentConditionsTest extends AccuweatherAbstractTest {

    @Test
    @DisplayName("Тест CurrentConditionsTest - поиск текущих погодных условий")
    @Description("Данный тест предназначен для поиска текущих погодных условий по ID City")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Вызов метода поиска текущих погодных условий по ID City")
    @Owner("Осипова Анна")
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
