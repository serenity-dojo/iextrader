package iextrader;

import iextrader.model.Client;
import iextrader.steps.PlatformAdminSteps;
import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static iextrader.model.Client.Builder.aClient;

@RunWith(SerenityRunner.class)
public class WhenRegisteringANewClient {

    @Before
    public void setBaseURL() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Steps
    PlatformAdminSteps pat;

    @Test
    public void each_new_client_should_be_given_a_unique_id() {

        Client sarahJane = aClient()
                .withFirstName("Sarah-Jane")
                .withLastName("Smith")
                .withEmail("sarah-jane@smith.com")
                .build();

        String clientId = pat.registerANewClient(sarahJane);

        pat.searchesForAClientById(clientId);

        pat.findsAClientMatching(sarahJane);
    }
}
