package io.github.krtkush.marsexplorer;

import android.app.Application;

import io.github.krtkush.marsexplorer.RESTClient.NASARestApiClient;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 21/05/16.
 */
public class MarsExplorer extends Application {

    // Variable that holds instance of the application class
    private static MarsExplorer marsExplorerInstance;
    // Variable that holds instance of the photos api interface
    private NASARestApiClient.NASAMarsPhotosApiInterface nasaMarsPhotosApiInterface;

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize timber logging tool only if in debug mode
        if(BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        marsExplorerInstance = this;
        nasaMarsPhotosApiInterface = NASARestApiClient.getNasaMarsPhotosApiInterface();
    }

    /**
     * @return Instance of the application class (App Context)
     */
    public static MarsExplorer getApplicationInstance() {
        return marsExplorerInstance;
    }

    /**
     * @return Instance of the interface
     */
    public NASARestApiClient.NASAMarsPhotosApiInterface getNasaMarsPhotosApiInterface() {
        return nasaMarsPhotosApiInterface;
    }
}
