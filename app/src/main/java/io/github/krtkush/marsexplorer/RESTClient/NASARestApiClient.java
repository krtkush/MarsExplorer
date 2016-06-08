package io.github.krtkush.marsexplorer.RESTClient;

import java.io.File;
import java.io.IOException;

import io.github.krtkush.marsexplorer.PicturesJsonDataModels.PhotoSearchResultDM;
import io.github.krtkush.marsexplorer.MarsExplorer;
import io.github.krtkush.marsexplorer.UtilityMethods;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 14/05/16.
 */
public class NASARestApiClient {

    private static NASAMarsPhotosApiInterface nasaMarsPhotosApiInterface;

    public static NASAMarsPhotosApiInterface getNasaMarsPhotosApiInterface() {

        if(nasaMarsPhotosApiInterface == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    // Enable response caching
                    .addNetworkInterceptor(new ResponseCacheInterceptor())
                    .addInterceptor(new OfflineResponseCacheInterceptor())
                    // Set the cache location and size (5 MB)
                    .cache(new Cache(new File(MarsExplorer.getApplicationInstance().getCacheDir(),
                            "apiResponses"), 5 * 1024 * 1024))
                    // Add the api key by default
                    .addInterceptor(new DefaultValuesInterceptor(RestClientConstants.apiKey))
                    // Enable logging
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            Retrofit retrofitClient = new Retrofit.Builder()
                    .baseUrl(RestClientConstants.baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            nasaMarsPhotosApiInterface = retrofitClient.create(NASAMarsPhotosApiInterface.class);
        }

        return nasaMarsPhotosApiInterface;
    }

    public interface NASAMarsPhotosApiInterface {

        @GET("{roverName}/photos")
        Observable<PhotoSearchResultDM> getPhotosBySol(
                @Header(RestClientConstants.offlineCachingFlagHeader) boolean offlineCacheFlag,
                @Header(RestClientConstants.responseCachingFlagHeader) boolean responseCacheFlag,
                @Path("roverName") String roverName,
                @Query("sol") String SOL,
                @Query("page") String pageNumber);
    }

    /**
     * Interceptor that, by default, adds required query parameter(s) in every API request.
     */
    private static class DefaultValuesInterceptor implements Interceptor {

        private String apiKey;

        public DefaultValuesInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("api_key", this.apiKey)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }
    }

    /**
     * Interceptor to cache data and maintain it for an hour.
     *
     * If the same network request is sent within a minute, the response is retrieved from cache.
     */
    private static class ResponseCacheInterceptor implements Interceptor {
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

    /**
     * Interceptor to cache data and maintain it for four weeks.
     *
     * If the device is offline, stale (at most four weeks old) response is fetched from the cache.
     */
    private static class OfflineResponseCacheInterceptor implements Interceptor {
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
}
