package searchBreweryTests;

import io.restassured.response.Response;
import org.GR8.api.body.searchBrewery.response.SearchBreweryResponse;
import org.GR8.api.steps.searchBreweryStep.SearchBreweryStep;
import org.GR8.api.utils.enums.StatusCodes;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.GR8.core.TestStepLogger.logStep;

public class SearchBreweryTests {
    private final SearchBreweryStep searchBreweryStep = new SearchBreweryStep();

    @DataProvider
    public Object[][] validQueryPerPageDataProvider() {
        return new Object[][]{
                {"brewery", "1"},
                {"dog", "4"},
                {"bottle", "10"},
                {"pub", "100"},
        };

    }

    @DataProvider
    public Object[][] invalidPerPageDataProvider() {
        return new Object[][]{
                {"brewery", "@"},
                {"dog", "abc"},
                {"bottle", "/per_page"},
                {"pub", "_/"},
        };

    }

    // In this case, some objects return names that do not contain an exact match to the search query.
    // Therefore, in this case, I would go to the developer and clarify how the api is configured in this case.
    // Perhaps this is not a bug but a feature. To show the user similar matches to his request.
    @Test(dataProvider = "validQueryPerPageDataProvider")
    public void searchBreweryWithValidSearchQuery(String searchQuery, String perPageCount) {
        logStep(1, "Get search brewery response");
        Response searchBreweryResponse = searchBreweryStep.searchBrewery(searchQuery, perPageCount);
        SearchBreweryResponse[] searchBrewery = searchBreweryResponse.as(SearchBreweryResponse[].class);

        logStep(2, "Check all response fields");

        // Get all the IDs and check if they are empty or null.
        boolean allIdsNotEmpty = searchBreweryStep.checkIfAllIdsAreEmpty(searchBrewery);
        // Get all the Names and check if they are contains search query.
        boolean allNameContainsSearchQuery = searchBreweryStep.checkIfAreAllNameContainsSearchQuery(searchBrewery, searchQuery);

        Assert.assertEquals(searchBreweryResponse.statusCode(), StatusCodes.OK.getCode(), "Status code is not expected");
        Assert.assertEquals(Integer.toString(searchBrewery.length), perPageCount, "The response contains the wrong quantity of brewery");
        Assert.assertTrue(allIdsNotEmpty, "Some ID is empty or null");
        Assert.assertTrue(allNameContainsSearchQuery, "Some name doesn't contains search query");
    }

    // In this version Api not implemented Error messages and my opinion this case should get 400 status code
    @Test(dataProvider = "invalidPerPageDataProvider")
    public void searchBreweryInvalidPerPage(String searchQuery, String perPageCount) {
        logStep(1, "Get search brewery response");
        Response searchBreweryResponse = searchBreweryStep.searchBrewery(searchQuery, perPageCount);

        logStep(2, "Check status code");
        Assert.assertEquals(searchBreweryResponse.statusCode(), StatusCodes.BAD_REQUEST.getCode(), "Status code is not expected");
        // There could be checks on the "message" field
        // if this API endpoint was configured
        // to show that the user entered an incorrect request
    }

    // In this case I think everything is working correctly.
    // If the search query is not specified, then we return an empty response.
    @Test
    public void searchBreweryWithoutSearchQuery() {
        logStep(1, "Get search brewery response");
        String perPage = "1";
        String searchQuery = "";
        Response searchBreweryResponse = searchBreweryStep.searchBrewery(searchQuery, perPage);
        SearchBreweryResponse[] searchBrewery = searchBreweryResponse.as(SearchBreweryResponse[].class);

        logStep(2, "Check status code");
        Assert.assertEquals(searchBreweryResponse.statusCode(), StatusCodes.OK.getCode(), "Status code is not expected");
        Assert.assertEquals(searchBrewery.length, 0, "The response contains the wrong quantity of brewery");
    }

    @Test
    public void searchBreweryWithoutPerPage() {
        logStep(1, "Get search brewery response");
        String searchQuery = "restaurant";
        Response searchBreweryResponse = searchBreweryStep.searchBreweryWithoutPerPage(searchQuery);
        SearchBreweryResponse[] searchBrewery = searchBreweryResponse.as(SearchBreweryResponse[].class);

        logStep(2, "Check all response fields");

        // Get all the IDs and check if they are empty or null.
        boolean allIdsNotEmpty = searchBreweryStep.checkIfAllIdsAreEmpty(searchBrewery);
        // Get all the Names and check if they are contains search query.
        boolean allNameContainsSearchQuery = searchBreweryStep.checkIfAreAllNameContainsSearchQuery(searchBrewery, searchQuery);

        Assert.assertEquals(searchBreweryResponse.statusCode(), StatusCodes.OK.getCode(), "Status code is not expected");
        Assert.assertTrue(allIdsNotEmpty, "Some ID is empty or null");
        Assert.assertTrue(allNameContainsSearchQuery, "Some name doesn't contains search query");
    }

    @Test
    public void searchBreweryIdempotency() {
        logStep(1, "Get search brewery response");
        String searchQuery = "dark";
        String perPageCount = "5";
        Response searchBreweryResponseFirst = searchBreweryStep.searchBrewery(searchQuery, perPageCount);
        SearchBreweryResponse[] searchBrewery = searchBreweryResponseFirst.as(SearchBreweryResponse[].class);

        logStep(2, "Check all response fields");

        // Get all the IDs and check if they are empty or null.
        boolean allIdsNotEmpty = searchBreweryStep.checkIfAllIdsAreEmpty(searchBrewery);
        // Get all the Names and check if they are contains search query.
        boolean allNameContainsSearchQuery = searchBreweryStep.checkIfAreAllNameContainsSearchQuery(searchBrewery, searchQuery);

        Response searchBreweryResponseSecond = searchBreweryStep.searchBrewery(searchQuery, perPageCount);

        Assert.assertEquals(searchBreweryResponseFirst.getBody().asString(),
                searchBreweryResponseSecond.getBody().asString(), "Second response is not equals first one");

        Assert.assertEquals(searchBreweryResponseFirst.statusCode(), StatusCodes.OK.getCode(), "Status code is not expected");
        Assert.assertEquals(Integer.toString(searchBrewery.length), perPageCount, "The response contains the wrong quantity of brewery");
        Assert.assertTrue(allIdsNotEmpty, "Some ID is empty or null");
        Assert.assertTrue(allNameContainsSearchQuery, "Some name doesn't contains search query");
    }

    @Test
    public void searchBreweryAutocompleteIdempotency() {
        logStep(1, "Get search brewery autocomplete response");
        String searchQuery = "brewing";
        Response searchBreweryResponseFirst = searchBreweryStep.searchBreweryAutocomplete(searchQuery);
        SearchBreweryResponse[] searchBrewery = searchBreweryResponseFirst.as(SearchBreweryResponse[].class);

        logStep(2, "Check all response fields");

        // Get all the IDs and check if they are empty or null.
        boolean allIdsNotEmpty = searchBreweryStep.checkIfAllIdsAreEmpty(searchBrewery);
        // Get all the Names and check if they are contains search query.
        boolean allNameContainsSearchQuery = searchBreweryStep.checkIfAreAllNameContainsSearchQuery(searchBrewery, searchQuery);

        Response searchBreweryResponseSecond = searchBreweryStep.searchBreweryAutocomplete(searchQuery);

        Assert.assertEquals(searchBreweryResponseFirst.getBody().asString(),
                searchBreweryResponseSecond.getBody().asString(), "Second response is not equals first one");

        Assert.assertEquals(searchBreweryResponseFirst.statusCode(), StatusCodes.OK.getCode(), "Status code is not expected");
        Assert.assertEquals(searchBrewery.length, 15, "The response should contains no more than 15 objects");
        Assert.assertTrue(allIdsNotEmpty, "Some ID is empty or null");
        Assert.assertTrue(allNameContainsSearchQuery, "Some name doesn't contains search query");
    }
}
