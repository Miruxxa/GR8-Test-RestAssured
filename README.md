# GR8-Test-RestAssured
Test task

"Test Task:
1) Cover "Search Breweries" method of https://www.openbrewerydb.org/documentation with autotests using Java + REST Assured
 (pick up to 5 scenarios covering main method features and implement corresponding autotest in code, the rest scenarios
  you think should be included in complete test suite can be listed in readme file).
2) Examine "List Breweries" method and share your thoughts (in readme file) on how you would apply test automation to it
 (what approach, test design techniques you'd choose etc). Also please provide estimated effort for completing this task."

Answer:
1) Since the endpoint contains both the search query and the number of responses per page.
We need to test the following scenarios:

Valid/Invalid - ("search query")

Valid/Invalid - ("per_page")

Without "search query"

Without "per_page"

Since the Get method is idempotent, we must also check this property.
That several identical requests return the same answer.

We also need to check that the correct fields are returned in the response.
Additionally, you can check that all the fields that should be returned are in the response.

Same as according to the "per_page" parameter
the correct number of objects in the response is returned


2) Since an endpoint can contain many parameters.
We can test or through a data provider.
Dynamically updating the endpoint and checking those fields that necessarily affect the result.

Or we can create a separate test for each parameter in the endpoint.

There are also specific parameters that are very convenient to check through the date provider, for example “by_type”.

Also, for some tests we will need to first receive a response with the full number of objects. And then send another request. To make sure we get the correct results.
For example, to test "https://api.openbrewerydb.org/v1/breweries?page=15&per_page=3"

We first need to get the basic answer "https://api.openbrewerydb.org/v1/breweries?per_page=3"

So that we can then correctly calculate the shift across objects.
