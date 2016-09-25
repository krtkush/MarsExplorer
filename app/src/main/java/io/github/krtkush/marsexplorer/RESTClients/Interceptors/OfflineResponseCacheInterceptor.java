package io.github.krtkush.marsexplorer.RESTClients.Interceptors;

import java.io.IOException;

import io.github.krtkush.marsexplorer.RESTClients.RestClientConstants;
import io.github.krtkush.marsexplorer.UtilityMethods;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 08/06/16.
 */

/**
 * Interceptor to cache data and maintain it for four weeks.
 *
 * If the device is offline, stale (at most four weeks old) response is fetched from the cache.
 */
public class OfflineResponseCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if(Boolean.valueOf(request.header(RestClientConstants.offlineCachingFlagHeader))) {
            Timber.i("Offline cache applied");
            if(!UtilityMethods.isNetworkAvailable()) {
                request = request.newBuilder()
                        .removeHeader(RestClientConstants.offlineCachingFlagHeader)
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 2419200)
                        .build();
            }
        } else
            Timber.i("Offline cache not applied");

        return chain.proceed(request);
    }
}
