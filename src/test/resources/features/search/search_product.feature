@feature:search 
Feature: Search product

	# searching orange product in the get API , verifying apple results 
  @search @negative
  Scenario Outline: Search test to verify response containing searched apple
  	Given I have a product <product> to search
  	When I call the product search GET endpoint
  	Then I receive success status code <statusCode>
  	And I verify that response contains the search product <searchProduct>
    Examples:	
      | product | statusCode | searchProduct |
      | orange  | 200        | apple         |
    
  #search for apple product in the get API ,verify mango results(unavailable product) hence failing the test case
  # (available product list are "orange", "apple", "pasta", "cola") as per given question    
  @search @negative
  Scenario Outline: Search test with available product and verify response for unavailable product mango
  	Given I have a product <product> to search
  	When I call the product search GET endpoint
  	Then I receive success status code <statusCode>
    And I validate response for searched product result <searchProduct> 
    And I verify search product is in available list
    Examples:	
      | product | statusCode | searchProduct |
      | apple   | 200        | mango         |
      
	
	#search for orange product and verify all the response list has orange 
  @search @negative
  Scenario Outline: Search test with available product and validate response
  	Given I have a product <product> to search
  	When I call the product search GET endpoint
  	Then I receive success status code <statusCode>
  	And The schema should match with the specification defined in <schemaValidator>
  	And I validate response for searched product result <product>
  	And I validate all the list in the response has searched product
    Examples:	
      | product | statusCode | schemaValidator       |
      | orange  | 200        | validSchemaValidator  |
      
  #Search for car in the GET API and validate the error status code received    
  @search @positive
  Scenario: Search test with unavailable product car
    Given I have a product <product> to search
    When I call the product search GET endpoint
    Then I verify <statusCode> not found error status code
    And I verify not found error message
    Examples:	
      | product | statusCode |
      | car     | 404        |
    

  