package iextrader;

import iextrader.model.Client;
import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.equalTo;

public class WhenMaintainingClientRecords {

    Client newClient;
    String clientId;

    @Before
    public void prepareTestData() {
        RestAssured.baseURI = "http://localhost:8080/api";

        // Given an existing client

        Client newClient = Client.Builder.aClient()
                .withFirstName("Sarah-Jane")
                .withLastName("Smith")
                .withEmail("sarah-jane@smith.com")
                .build();

        given().contentType("application/json").
                and().body(newClient)
                .when().post("/client")
                .then().statusCode(200);

        clientId = SerenityRest.lastResponse().jsonPath().getString("id");

    }

    @Test
    public void should_be_able_to_update_an_existing_client() {

        Client updatedClient = Client.Builder.aClient().withFirstName("Sarah-Jane")
                .withLastName("Smith")
                .withEmail("sarah-jane.smith@myemail.com")
                .build();

        given().contentType("application/json")
                .and().body(updatedClient)
                .and().pathParam("id",clientId)
                .when().put("/client/{id}")
                .then().statusCode(200);

        given().pathParam("id", clientId)
                .when().get("/client/{id}")
                .then().body("email", equalTo("sarah-jane.smith@myemail.com"));

    }

    @Test
    public void should_remove_client_from_the_registry() {

        // When I delete the client

        SerenityRest
                .given().pathParam("id", clientId)
                .when().delete("/client/{id}")
                .then().statusCode(204);

        // Then the client should no longer be found

        SerenityRest.given().pathParam("id",clientId)
                .when().get("/client/{id}")
                .then().statusCode(404);
    }
}
