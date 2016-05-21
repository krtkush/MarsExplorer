package io.github.krtkush.marsexplorer;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 21/05/16.
 */
public class MarsExplorer extends Application {

    private static MarsExplorer marsExplorerInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        marsExplorerInstance = this;

        //Initialize timber logging tool only if in debug mode
        if(BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    public static MarsExplorer getInstance() {

        return marsExplorerInstance;
    }
}
