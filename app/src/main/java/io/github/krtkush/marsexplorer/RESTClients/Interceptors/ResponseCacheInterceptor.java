package io.github.krtkush.marsexplorer.RESTClients.Interceptors;

import java.io.IOException;

import io.github.krtkush.marsexplorer.RESTClients.RestClientConstants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 08/06/16.
 */

/**
 * Interceptor to cache data and maintain it for an hour.
 *
 * If the same network request is sent within a minute, the response is retrieved from cache.
 */
public class ResponseCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if(Boolean.valueOf(request.header(RestClientConstants.responseCachingFlagHeader))) {
            Timber.i("Response cache applied");
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .removeHeader(RestClientConstants.responseCachingFlagHeader)
                    .header("Cache-Control", "public, max-age=" + 3600)
                    .build();
        } else {
            Timber.i("Response cache not applied");
            return chain.proceed(chain.request());
        }
    }
}
