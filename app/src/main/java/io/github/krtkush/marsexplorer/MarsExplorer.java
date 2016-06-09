package io.github.krtkush.marsexplorer;

import android.app.Application;

import io.github.krtkush.marsexplorer.RESTClient.MAASRestApiClient;
import io.github.krtkush.marsexplorer.RESTClient.NASARestApiClient;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 21/05/16.
 */
public class MarsExplorer extends Application {

    // Variable that holds instance of the application class
    private static MarsExplorer marsExplorerInstance;
    // Variable that holds instance of the photos API interface
    private NASARestApiClient.NASAMarsPhotosApiInterface nasaMarsPhotosApiInterface;
    // Variable to hold instance of the weather API interface
    private MAASRestApiClient.MAASWeatherApiInterface maasWeatherApiInterface;

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize timber logging tool only if in debug mode
        if(BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        marsExplorerInstance = this;
        nasaMarsPhotosApiInterface = NASARestApiClient.getNasaMarsPhotosApiInterface();
        maasWeatherApiInterface = MAASRestApiClient.getMaasWeatherApiInterface();
    }

    /**
     * @return Instance of the application class (App Context)
     */
    public static MarsExplorer getApplicationInstance() {
        return marsExplorerInstance;
    }

    /**
     * @return Instance of the NASAMarsPhotosApiInterface
     */
    public NASARestApiClient.NASAMarsPhotosApiInterface getNasaMarsPhotosApiInterface() {
        return nasaMarsPhotosApiInterface;
    }

    /**
     * @return Instance of MAASWeatherApiInterface
     */
    public MAASRestApiClient.MAASWeatherApiInterface getMaasWeatherApiInterface() {
        return maasWeatherApiInterface;
    }
}
