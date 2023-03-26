package search.actions;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import search.common.AvailableProductClass;
import search.common.RequestSpec;

public class SearchProductActions {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonActions.class);
	private int jsonSize;
	int searchProductSize;
	int listHavingUnsearchedProducts;

	@When("Get response from search product {string}")
	public Response callSearchEndPoint(String product) {
		RequestSpecification reqSpecification=RequestSpec.searchReqSpec();
		return SerenityRest.given().log().uri().spec(reqSpecification).get(product);
	}

	@Then("Verify response for containing search product")
	public void verifyResponseContainsProduct(String displayedProduct){
		List<String> titles = lastResponse().jsonPath().getList("title");
		assertEquals("Fails test case when serached product is not present in the response" ,"true",Stream.of(titles.contains(displayedProduct)).findAny().get());
	}

	@And("Verify response")
	public void validateResponseWithSearchedProduct(String displayedProduct) {
		List<String> titles = lastResponse().jsonPath().getList("title");
		jsonSize=titles.size();
		searchProductSize = (int) titles.stream().filter(a->a.toLowerCase().contains(displayedProduct)).count();
		assertTrue("Count of list in response not having "+displayedProduct+" is zero",searchProductSize>0);
		listHavingUnsearchedProducts=jsonSize-searchProductSize;
		LOGGER.info("Count of list in response not having {} are {} ", displayedProduct, listHavingUnsearchedProducts) ;
	}

	@And("Verify response list with searched product")
	public void verifyProductResponseAgainstResult() {
		assertEquals("Test Fails for searched product actual list count in response is not equal is ", jsonSize, searchProductSize);
	}

	@And("Check product in available list")
	public void verifyProductAvailableOrNot(String dispProduct) {
		System.out.println();
		assertTrue(String.format("Fails test as %s is not available in available product list please pick from orange or apple or pasta or cola ",dispProduct), AvailableProductClass.contains(dispProduct));
	}
}
