package io.github.krtkush.marsexplorer.RESTClient;

/**
 * Created by kartikeykushwaha on 14/05/16.
 */
public class Constants {

    public static final String apiKey = "DEMO_KEY";

    // Strings related to api URLs
    private static final String apiEndPoint = "https://api.nasa.gov/";
    private static final String apiName = "mars-photos/";
    private static final String apiVersion = "api/v1/rovers/";
    public static final String baseUrl = apiEndPoint + apiName + apiVersion;

    //Rover names
    public static final String Curiousity = "curiousity";
    public static final String Opportunity = "opportunity";
    public static final String Spirit = "spirit";
}
