package search.actions;

import io.cucumber.java.en.Then;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.core.environment.EnvironmentSpecificConfiguration.from;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.then;
import static org.assertj.core.api.Assertions.assertThat;


public class CommonActions {

	protected static String schemaPath;
	EnvironmentVariables environmentVariables = Injectors.getInjector()
			.getInstance(EnvironmentVariables.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonActions.class);

	@Then("Verify that API response is {int}")
	public void responseCodeIs(int responseCode) {
		then().statusCode(responseCode);
	}

	@Then("Verify response schema with valid schema json")
	public void verifyResponseSchema(String schemaJson) {
		System.out.println(schemaJson);
		then().body(matchesJsonSchemaInClasspath(from(environmentVariables).getProperty(schemaJson)));
	}

	@Then("Verify that response list isn't an empty list")
	public void responseShouldNotBeEmptyList() {
		List<HashMap<String, Object>> res = lastResponse().getBody().jsonPath().getList("$");
		LOGGER.info("Response list size is {}", res.size());
		assertThat(res.size()).isNotZero();
	}
}
