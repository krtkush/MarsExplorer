package io.github.krtkush.marsexplorer.RESTClients;

import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.github.krtkush.marsexplorer.BuildConfig;
import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.PhotosJsonDataModels.PhotosResultDM;
import io.github.krtkush.marsexplorer.RESTClients.Interceptors.OfflineResponseCacheInterceptor;
import io.github.krtkush.marsexplorer.RESTClients.Interceptors.ResponseCacheInterceptor;
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

/**
 * Created by kartikeykushwaha on 14/05/16.
 */
public class NASARestApiClient {

    private static NASAMarsPhotosApiInterface nasaMarsPhotosApiInterface;

    public static NASAMarsPhotosApiInterface getNasaMarsPhotosApiInterface() {

        if(nasaMarsPhotosApiInterface == null) {

            // Log level depending on build type. No logging in case of production APK
            HttpLoggingInterceptor.Level logLevel;

            if(BuildConfig.DEBUG)
                logLevel = HttpLoggingInterceptor.Level.BODY;
            else
                logLevel = HttpLoggingInterceptor.Level.NONE;

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    // Enable response caching
                    .addNetworkInterceptor(new ResponseCacheInterceptor())
                    .addInterceptor(new OfflineResponseCacheInterceptor())
                    // Set the cache location and size (5 MB)
                    .cache(new Cache(new File(MarsExplorerApplication.getApplicationInstance()
                            .getCacheDir(), RestClientConstants.apiResponsesCache),
                            5 * 1024 * 1024))
                    // Add the api key by default
                    .addInterceptor(new DefaultValuesInterceptor(RestClientConstants.apiKey))
                    // Enable logging
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(logLevel))
                    // Set the timeout periods
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                            .create()
            );

            Retrofit retrofitClient = new Retrofit.Builder()
                    .baseUrl(RestClientConstants.nasaApiBaseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            nasaMarsPhotosApiInterface = retrofitClient.create(NASAMarsPhotosApiInterface.class);
        }

        return nasaMarsPhotosApiInterface;
    }

    public interface NASAMarsPhotosApiInterface {

        @GET("{roverName}/photos")
        Observable<PhotosResultDM> getPhotosBySol(
                @Header(RestClientConstants.offlineCachingFlagHeader) boolean offlineCacheFlag,
                @Header(RestClientConstants.responseCachingFlagHeader) boolean responseCacheFlag,
                @Path("roverName") String roverName,
                @Query("sol") String SOL);
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
}
