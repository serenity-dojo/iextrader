package iextrader;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SerenityRunner.class)
public class WhenUpdatingSharePrices {

    @Before
    public void setBaseURL() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void should_be_able_to_update_a_share_price_in_the_dev_environment() {

        given().contentType("application/json")
                .and().body("499.99")
                .and().pathParam("stock","aapl")
                .and().post("/stock/{stock}/price");

        when().get("/stock/{stock}/price", "aapl")
              .then().statusCode(200);

        String updatedPrice = SerenityRest.lastResponse().body().asString();

        assertThat(updatedPrice).isEqualTo("499.99");

    }
}
