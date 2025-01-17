package MockTests;

import Location.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static HW.AccuweatherAbstractTest.getApiKey;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoricalConditionsTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(HistoricalConditionsTest.class);

    @Test
    void Test24Hours() throws IOException, URISyntaxException {
        logger.info("Тест на запрос historical conditions запущен");

        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodyOk = new Location();
        bodyOk.setKey("OK");

        Location bodyError = new Location();
        bodyError.setKey("Error");

        stubFor(get(urlPathEqualTo("forecasts/{version}/hourly/1hour/{ID}"))
                .withQueryParam("apikey", equalTo(getApiKey()))
                .withQueryParam("version", equalTo("v1"))
                .withQueryParam("ID", equalTo("215854"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyOk))));

        stubFor(get(urlPathEqualTo("forecasts/{version}/hourly/1hour/{ID}"))
                .withQueryParam("apikey", equalTo("111111"))
                .withQueryParam("version", equalTo("v1"))
                .withQueryParam("ID", equalTo("215854"))
                .willReturn(aResponse()
                        .withStatus(401).withBody(mapper.writeValueAsString(bodyError))));

        logger.debug("Мокирование для теста на запрос historical conditions завершено");
        //when
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"forecasts/{version}/hourly/1hour/{ID}");


        URI uri = new URIBuilder(request.getURI())
                .addParameter("ID", "215854")
                .build();
        request.setURI(uri);

        HttpResponse responseOk = client.execute(request);

        URI uriError = new URIBuilder(request.getURI())
                .addParameter("ID", "111111")
                .build();
        request.setURI(uriError);

        HttpResponse responseError = client.execute(request);

        //then

        verify(2, getRequestedFor(urlPathEqualTo("forecasts/{version}/hourly/1hour/{ID}")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
        assertEquals("OK", convertResponseToString(responseOk));
        assertEquals(401, responseError.getStatusLine().getStatusCode());
        assertEquals("Unauthorized", convertResponseToString(responseError));


    }

}

