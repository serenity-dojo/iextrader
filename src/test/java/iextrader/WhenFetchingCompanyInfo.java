package iextrader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class WhenFetchingCompanyInfo {

    @Before
    public void setBaseURLs() {
        RestAssured.baseURI = "https://api.iextrading.com/1.0";
    }

    @Test
    public void should_succeed_for_listed_companies() {
        System.out.println(when().get("/stock/aapl/company").getBody().print());

        when().get("/stock/aapl/company")
                .then().statusCode(lessThan(300));
    }

    @Test
    public void should_return_expected_content_type() {

        Serenity.recordReportData().asEvidence().withTitle("response").andContents(
                when().get("/stock/aapl/company").getBody().print()
        );

            when().get("/stock/aapl/company")
                    .then().contentType(ContentType.JSON)
                    .headers("Content-Type", "application/json; charset=utf-8",
                            "Content-Encoding", "gzip");
    }

    @Test
    public void should_include_company_name_and_industry() {

        Serenity.recordReportData().asEvidence().withTitle("response").andContents(
                given().pathParam("symbol", "ibm").
                        when().get("/stock/{symbol}/company").getBody().print()
        );

        given().pathParam("symbol", "ibm").
                when().get("/stock/{symbol}/company")
                .then().statusCode(200);

        Ensure.that("company name is returned",  response -> response.body("companyName",
                                                            equalTo("International Business Machines Corporation")))
                .andThat("industry is returned", response -> response.body("industry",
                                                            equalTo("Application Software")));
    }


    @Test
    public void should_have_correct_company_name() {
        Serenity.recordReportData().asEvidence().withTitle("response").andContents(
                when().get("/stock/aapl/company").getBody().print()
        );

        when().get("/stock/aapl/company").then()
                .body("companyName", name -> equalTo("Apple Inc."));

    }

    @Test
    public void should_have_correct_name() {
        Serenity.recordReportData().asEvidence().withTitle("response").andContents(
                when().get("/stock/aapl/company").getBody().print()
        );

        when().get("/stock/aapl/company").then()
                .body("companyName", name -> equalTo("Apple Inc."));

    }

    @Test
    public void should_return_a_ctoken_cookie() {
        Serenity.recordReportData().asEvidence().withTitle("response").andContents(
                when().get("/stock/aapl/company").getBody().print()
        );

        when().get("/stock/aapl/company").then()
                .cookie("ctoken", notNullValue());
    }

    @Test
    public void should_have_correct_tags() {
        Serenity.recordReportData().asEvidence().withTitle("response").andContents(
                when().get("/stock/aapl/company").getBody().print()
        );

        when().get("/stock/aapl/company").then()
                .body("tags", hasItems("Technology","Computer Hardware"));

    }
}
