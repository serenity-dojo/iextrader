package iextrader;

import iextrader.model.Trade;
import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class WhenFetchingBookData {

    @Before
    public void setBaseURLs() {
        RestAssured.baseURI = "https://api.iextrading.com/1.0";
    }

    @Test
    public void all_trades_should_have_a_trade_id() {
        when().get("/stock/aapl/book").then().statusCode(200);

        List<Trade> trades = SerenityRest.lastResponse().jsonPath().getList("trades", Trade.class);

        trades.forEach(
                trade -> {
                    assertThat(trade.getTradeId()).isGreaterThan(0);
                    assertThat(trade.getTimestamp()).isGreaterThan(0);
                }
        );

    }

    @Test
    public void quote_should_contain_correct_stock_data() {
        when().get("/stock/aapl/book")
                .then().body("quote.symbol",equalTo("AAPL"));
    }
}
