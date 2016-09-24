package io.github.krtkush.marsexplorer.RESTClient;

/**
 * Created by kartikeykushwaha on 14/05/16.
 */
public class RestClientConstants {

    // NASA API key
    public static final String apiKey = "pnfYfjK3FhG77VCRO2ZwtjI4w9hq0fXRozkZ0VbY";

    // Strings related to NASA's API
    private static final String nasaApiEndPoint = "https://api.nasa.gov/";
    private static final String nasaApiServiceName = "mars-photos/";
    private static final String nasaApiVersion = "api/v1/rovers/";
    public static final String nasaApiBaseUrl = nasaApiEndPoint + nasaApiServiceName
            + nasaApiVersion;

    // Strings related to MAAS's API
    private static final String marsWeatherEndPoint = "http://cab.inta-csic.es/";
    private static final String marsWeatherAddress = "rems/wp-content/plugins/marsweather-widget/";
    public static final String marsWeatherBaseUrl = marsWeatherEndPoint + marsWeatherAddress;

    // Custom headers
    public static final String responseCachingFlagHeader = "ApplyResponseCache";
    public static final String offlineCachingFlagHeader = "ApplyOfflineCache";

    // Cache directory name
    public static final String apiResponsesCache = "apiResponses";
}
