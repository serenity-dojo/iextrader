package iextrader;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
public class WhenRegisteringANewClient {

    @Before
    public void setBaseURL() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void each_new_client_should_be_given_a_unique_id() {

        String newClient = "{\n" +
                "  \"email\": \"sarah-jane@smith.com\",\n" +
                "  \"firstName\": \"Sarah-Jane\",\n" +
                "  \"lastName\": \"Smith\"\n" +
                "}";

        given().contentType("application/json").
                and().body(newClient)
                .when().post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)));

        String clientId = SerenityRest.lastResponse().jsonPath().getString("id");

        given().pathParam("id", clientId)
                .when().get("/client/{id}")
                .then().statusCode(200)
                .and().body("email",equalTo("sarah-jane@smith.com"))
                .and().body("firstName",equalTo("Sarah-Jane"))
                .and().body("lastName",equalTo("Smith"));

    }
}
