package org.GR8.api.utils.api;

import org.GR8.api.utils.api.ApiEndPoints;
import org.GR8.core.ProdProperties;
import org.GR8.core.TestInitValues;
import org.aeonbits.owner.ConfigFactory;

public class ApiPaths {

    public static final TestInitValues testCred = ConfigFactory.create(ProdProperties.class);
    public static final String BASE_API_URL = testCred.baseApiUrl();
    public static final String SEARCH_BREWERY_PATH = String.format("%s%s", BASE_API_URL, ApiEndPoints.SEARCH_BREWERY);
    public static final String SEARCH_BREWERY_WITHOUT_PER_PAGE_PATH = String.format("%s%s", BASE_API_URL, ApiEndPoints.SEARCH_BREWERY_WITHOUT_PER_PAGE);
    public static final String SEARCH_BREWERY_AUTOCOMPLETE_PATH = String.format("%s%s", BASE_API_URL, ApiEndPoints.SEARCH_BREWERY_AUTOCOMPLETE);

}
