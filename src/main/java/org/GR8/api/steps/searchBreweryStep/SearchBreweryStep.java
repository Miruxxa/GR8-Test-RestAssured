package org.GR8.api.steps.searchBreweryStep;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.GR8.api.body.searchBrewery.response.SearchBreweryResponse;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.GR8.api.utils.api.ApiPaths.*;
import static org.GR8.api.utils.api.BaseApiRequest.baseGetRequest;

public class SearchBreweryStep {

    @Step
    public Response searchBrewery(String searchQuery, String perPage) {
        return baseGetRequest(String.format(SEARCH_BREWERY_PATH, searchQuery, perPage));
    }

    @Step
    public Response searchBreweryWithoutPerPage(String searchQuery) {
        return baseGetRequest(String.format(SEARCH_BREWERY_WITHOUT_PER_PAGE_PATH, searchQuery));
    }

    @Step
    public Response searchBreweryAutocomplete(String searchQuery) {
        return baseGetRequest(String.format(SEARCH_BREWERY_AUTOCOMPLETE_PATH, searchQuery));
    }

    @Step
    public boolean checkIfAllIdsAreEmpty(SearchBreweryResponse[] searchBrewery) {
        Stream<String> idStream = Arrays.stream(searchBrewery)
                .map(SearchBreweryResponse::getId);
        return idStream.allMatch(id -> id != null && !id.isEmpty());
    }

    @Step
    public boolean checkIfAreAllNameContainsSearchQuery(SearchBreweryResponse[] searchBrewery, String searchQuery) {
        Stream<String> nameStream = Arrays.stream(searchBrewery)
                .map(SearchBreweryResponse::getName);
        return nameStream.allMatch(name -> name != null && !name.isEmpty() && name.toLowerCase().contains(searchQuery.toLowerCase()));
    }
}
