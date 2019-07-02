package no.nav;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.TargetRequestFilter;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Provider("ArticlesProvider")
@PactBroker(host = "localhost", port = "8090")
public class ProviderTest {

    private WireMockServer wireMockServer;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {

        context.verifyInteraction();
    }

    @BeforeAll
    static void setUpService() {
        //Run DB, create schema
        //Run service
        //...
        //LOGGER.info("BeforeAll - setUpService ");
        System.out.println("Runs Setup Service");

        //Connecting to remote server
        WireMock.configureFor("localhost", 8080);

        System.setProperty("pact.verifier.publishResults", "true");
        System.setProperty("pact.provider.version", "2.0");
    }

    @TargetRequestFilter
    public void exampleRequestFilter(HttpRequest request) {
        // Authorization header Base64 encoded...
        //String encoded = Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        //request.addHeader("Authorization", "Basic " + encoded);
    }

    @AfterAll
    static void tearDownService() {
        //Run DB, create schema
        //Run service
        //...
        //LOGGER.info("BeforeAll - setUpService ");
        System.out.println("Runs Teardown Service");

        //Resets Wiremock Server
        WireMock.reset();

    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8080, "/"));
    }

    @State("Pact for Issue 313")
    public void someProviderState() {

        System.out.println("Issue 313 is present in the system");

        // Returns JSON from File
        WireMock.stubFor(get(urlEqualTo("/person.json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("svp/Person.json"))
        );

        // Returns JSON from File
        WireMock.stubFor(get(urlEqualTo("/articles.json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("svp/resource.json"))
        );
    }

    @State("Pact for Issue 314")
    public void someotherProviderState() {
        System.out.println("Issue 314 is present in the system");

        // Returns JSON from File
        WireMock.stubFor(get(urlEqualTo("/getarticles"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("svp/Person.json"))
        );
    }

}