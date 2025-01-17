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

public class CurrentConditionsTest extends AbstractTest{


    private static final Logger logger
            = LoggerFactory.getLogger(CurrentConditionsTest.class);

    @Test
    void GetConditionsTest() throws IOException, URISyntaxException{
        logger.info("Тест на get Conditions запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodyOk = new Location();
        bodyOk.setKey("OK");

        stubFor(get(urlPathEqualTo("currentconditions/{version}/{ID}"))
                .withQueryParam("apikey", equalTo(getApiKey()))
                .withQueryParam("version", equalTo("v1"))
                .withQueryParam("ID", equalTo("215854"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyOk))));

        logger.debug("Мокирование для теста на get Conditions завершено");
        //when
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"currentconditions/{version}/{ID}");

        URI uri = new URIBuilder(request.getURI())
                .addParameter("ID", "215854")
                .build();
        request.setURI(uri);

        HttpResponse responseOk = client.execute(request);

        //then

        verify(2, getRequestedFor(urlPathEqualTo("currentconditions/{version}/{ID}")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
    }
}
