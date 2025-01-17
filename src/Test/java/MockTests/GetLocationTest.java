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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetLocationTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(GetLocationTest.class);

    @Test
    void TestReturn200() throws IOException, URISyntaxException {
        logger.info("Тест на код ответа 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodyOk = new Location();
        bodyOk.setKey("OK");

        Location bodyError = new Location();
        bodyError.setKey("Error");

        stubFor(get(urlPathEqualTo("locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Tel Aviv"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyOk))));

        stubFor(get(urlPathEqualTo("locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("error"))
                .willReturn(aResponse()
                        .withStatus(400).withBody(mapper.writeValueAsString(bodyError))));

        logger.debug("Мокирование для теста на код ответа 200 завершено");
        //when
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"locations/v1/cities/autocomplete");

        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "Tel Aviv")
                .build();
        request.setURI(uri);

        HttpResponse responseOk = client.execute(request);

        URI uriError = new URIBuilder(request.getURI())
                .addParameter("q", "error")
                .build();
        request.setURI(uriError);

        HttpResponse responseError = client.execute(request);

        //then

        verify(2, getRequestedFor(urlPathEqualTo("locations/v1/cities/autocomplete")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
        assertEquals(400, responseError.getStatusLine().getStatusCode());


    }

    @Test
    void TestError401() throws IOException, URISyntaxException {
        logger.info("Тест на код ответа 401 запущен");
        //given
        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("apiKey", notMatching("82c9229354f849e78efe010d94150807"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("ERROR")));
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "Cc4S0AMns19ceUhITAzfm93GxUxn0N1i")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = client.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(401, response.getStatusLine().getStatusCode());

    }
}