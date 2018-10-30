package iextrader.steps;

import iextrader.model.Client;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class PlatformAdminSteps {

    private String actor;

    @Step("#actor registers a new client {0}")
    public String registerANewClient(Client newClient) {
        given().contentType("application/json")
                .and().body(newClient)
                .when().post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)));

        return SerenityRest.lastResponse().jsonPath().getString("id");
    }

    @Step("#actor searches for a client with id {0}")
    public void searchesForAClientById(String clientId) {
        when().get("/client/{id}", clientId)
              .then().statusCode(200);
    }

    @Step("#actor finds a client matching {0}")
    public void findsAClientMatching(Client expectedClient) {
        Ensure.that("first name is " + expectedClient.getFirstName(),
                    response -> response.body("firstName", equalTo(expectedClient.getFirstName())));

        Ensure.that("lqst name is " + expectedClient.getLastName(),
                response -> response.body("lastName", equalTo(expectedClient.getLastName())));

        Ensure.that("email is " + expectedClient.getEmail(),
                response -> response.body("email", equalTo(expectedClient.getEmail())));
    }
}
