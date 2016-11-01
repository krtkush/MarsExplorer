package io.github.krtkush.marsexplorer;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import io.github.krtkush.marsexplorer.RESTClients.MarsWeatherClient;
import io.github.krtkush.marsexplorer.RESTClients.NASARestApiClient;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by kartikeykushwaha on 21/05/16.
 */
public class MarsExplorerApplication extends Application {

    // Variable that holds instance of the application class
    private static MarsExplorerApplication marsExplorerApplicationInstance;
    // Variable that holds instance of the photos API interface
    private NASARestApiClient.NASAMarsPhotosApiInterface nasaMarsPhotosApiInterface;
    // Variable to hold instance of the weather API interface
    private MarsWeatherClient.MarsWeatherInterface marsWeatherInterface;

    @Override
    public void onCreate() {
        super.onCreate();

        marsExplorerApplicationInstance = this;

        // Initialize the API interfaces.
        nasaMarsPhotosApiInterface = NASARestApiClient.getNasaMarsPhotosApiInterface();
        marsWeatherInterface = MarsWeatherClient.getMarsWeatherInterface();

        // Initialize timber logging tool only if in debug mode.
        if(BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        // Initialize calligraphy with the preferred font.
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        // Initialize picasso cache indicator and logging only if in debug mode.
        if(BuildConfig.DEBUG) {
            Picasso
                    .with(marsExplorerApplicationInstance)
                    .setIndicatorsEnabled(true);

            Picasso
                    .with(marsExplorerApplicationInstance)
                    .setLoggingEnabled(true);
        }

        // Initialize Leak Canary
        if(BuildConfig.DEBUG)
            LeakCanary.install(this);
    }

    /**
     * @return Instance of the application class (App Context)
     */
    public static MarsExplorerApplication getApplicationInstance() {
        return marsExplorerApplicationInstance;
    }

    /**
     * @return Instance of the NASAMarsPhotosApiInterface
     */
    public NASARestApiClient.NASAMarsPhotosApiInterface getNasaMarsPhotosApiInterface() {
        return nasaMarsPhotosApiInterface;
    }

    /**
     * @return Instance of MarsWeatherInterface
     */
    public MarsWeatherClient.MarsWeatherInterface getMarsWeatherInterface() {
        return marsWeatherInterface;
    }
}
