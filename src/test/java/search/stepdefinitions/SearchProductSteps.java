package search.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import search.actions.CommonActions;
import search.actions.SearchProductActions;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;

public class SearchProductSteps {

	protected static String searchProduct;
	protected static String displayedProduct;

	@Steps
	public CommonActions commonActions;

	@Steps
	public SearchProductActions searchProductActions;

	@Given("^I have a product (.*) to search$")
	public void searchProduct(String product) {
		searchProduct=product;	
	}

	@When("^I call the product search GET endpoint")
	public void callSearchEndPoint() {
		searchProductActions.callSearchEndPoint(searchProduct);
	}

	@Then("I receive success status code (.*)$")
	public void verifySucessStatusCode(int statusCode){ commonActions.responseCodeIs(statusCode);
	}

	@Then("^I verify (.*) not found error status code")
	public void notFoundStatusCode(int statusCode) {
		commonActions.responseCodeIs(statusCode);
	}

	@And("I verify not found error message")
	public void notFoundErrorResult() {
		then().body("detail.error", is(true));
	}

	@And("^I verify that response contains the search product (.*)$")
	public void searchProductInResponse(String dispProduct) {
		displayedProduct=dispProduct;
		commonActions.responseShouldNotBeEmptyList();
		searchProductActions.verifyResponseContainsProduct(displayedProduct);

	}

	@And("^I validate response for searched product result (.*)$")
	public void validateResponse(String dispProduct) {
		displayedProduct=dispProduct;
		searchProductActions.validateResponseWithSearchedProduct(displayedProduct);
	}

	@And("I validate all the list in the response has searched product")
	public void verifyResponseList() {
		searchProductActions.verifyProductResponseAgainstResult();

	}

	@And("^The schema should match with the specification defined in (.*)$")
	public void the_schema_should_match_with_the_specification(String spec) {
		commonActions.verifyResponseSchema(spec);
	}
	
	@And("I verify search product is in available list")
	public void verifyProductIsAvailableOrNot() {
		searchProductActions.verifyProductAvailableOrNot(displayedProduct);
	}
}
