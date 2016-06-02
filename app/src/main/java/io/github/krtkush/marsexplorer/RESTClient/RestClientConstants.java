package io.github.krtkush.marsexplorer.RESTClient;

/**
 * Created by kartikeykushwaha on 14/05/16.
 */
public class RestClientConstants {

    public static final String apiKey = "DEMO_KEY";

    // Strings related to api URLs
    private static final String apiEndPoint = "https://api.nasa.gov/";
    private static final String apiName = "mars-photos/";
    private static final String apiVersion = "api/v1/rovers/";
    public static final String baseUrl = apiEndPoint + apiName + apiVersion;

    //Custom headers
    public static final String responseCachingFlagHeader = "ApplyResponseCache";
    public static final String offlineCachingFlagHeader = "ApplyOfflineCache";
}
