package io.github.krtkush.marsexplorer.RESTClient;

/**
 * Created by kartikeykushwaha on 14/05/16.
 */
public class RestClientConstants {

    // NASA API key
    public static final String apiKey = "DEMO_KEY";

    // Strings related to NASA's API URLs
    private static final String nasaApiEndPoint = "https://api.nasa.gov/";
    private static final String nasaApiServiceName = "mars-photos/";
    private static final String nasaApiVersion = "api/v1/rovers/";
    public static final String nasaApiBaseUrl = nasaApiEndPoint + nasaApiServiceName + nasaApiVersion;

    // Strings related to MAAS's API URLs
    private static final String maasApiEndPoint = "http://marsweather.ingenology.com/";
    private static final String maasApiVersion = "v1/";
    public static final String maasApiBaseUrl = maasApiEndPoint + maasApiVersion;

    // Custom headers
    public static final String responseCachingFlagHeader = "ApplyResponseCache";
    public static final String offlineCachingFlagHeader = "ApplyOfflineCache";
}
