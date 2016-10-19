package io.github.krtkush.marsexplorer.RESTClients;

/**
 * Created by kartikeykushwaha on 14/05/16.
 */
public class RestClientConstants {

    // Strings related to NASA's API
    private static final String nasaApiEndPoint = "https://api.nasa.gov/";
    private static final String nasaApiServiceName = "mars-photos/";
    private static final String nasaApiVersion = "api/v1/rovers/";
    public static final String nasaApiBaseUrl = nasaApiEndPoint + nasaApiServiceName
            + nasaApiVersion;

    // Strings related to Mars Weather's API
    private static final String marsWeatherEndPoint = "http://cab.inta-csic.es/";
    private static final String marsWeatherAddress = "rems/wp-content/plugins/marsweather-widget/";
    public static final String marsWeatherBaseUrl = marsWeatherEndPoint + marsWeatherAddress;

    // Custom headers
    public static final String responseCachingFlagHeader = "ApplyResponseCache";
    public static final String offlineCachingFlagHeader = "ApplyOfflineCache";

    // Cache directory name
    public static final String apiResponsesCache = "apiResponses";
}
