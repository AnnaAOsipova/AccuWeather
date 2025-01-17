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

class TenDaysSpecIndexTest extends AbstractTest {
    private static final Logger logger
            = LoggerFactory.getLogger(TenDaysSpecIndexTest.class);

    @Test
    void SpecificIndex401ErrorTest() throws IOException, URISyntaxException {
        logger.info("Тест на код ответа 401 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodyOk = new Location();
        bodyOk.setKey("OK");

        Location bodyError = new Location();
        bodyError.setKey("Error");

        stubFor(get(urlPathEqualTo("forecasts/v1/daily/10day/ID"))
                .withQueryParam("ID", equalTo("215854"))
                .willReturn(aResponse()
                        .withStatus(401).withBody(mapper.writeValueAsString(bodyOk))));

        stubFor(get(urlPathEqualTo("forecasts/v1/daily/10day/ID"))
                .withQueryParam("ID", equalTo("111111"))
                .willReturn(aResponse()
                        .withStatus(400).withBody(mapper.writeValueAsString(bodyError))));

        logger.debug("Мокирование для теста на код ответа 401 завершено");
        //when
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "forecasts/v1/daily/10day/ID");

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

        verify(2, getRequestedFor(urlPathEqualTo("forecasts/v1/daily/10day/ID")));
        assertEquals(401, responseOk.getStatusLine().getStatusCode());
        assertEquals(400, responseError.getStatusLine().getStatusCode());


    }
}
