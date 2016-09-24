package io.github.krtkush.marsexplorer.RESTClient;

import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import java.io.File;

import io.github.krtkush.marsexplorer.BuildConfig;
import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.WeatherJsonDataModel.MarsWeatherResultDM;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by kartikeykushwaha on 08/06/16.
 */
public class MarsWeatherClient {

    private static MarsWeatherInterface marsWeatherInterface;

    public static MarsWeatherInterface getMarsWeatherInterface() {

        if(marsWeatherInterface == null) {

            // Log level depending on build type. No logging in case of production APK
            HttpLoggingInterceptor.Level logLevel;

            if(BuildConfig.DEBUG)
                logLevel = HttpLoggingInterceptor.Level.BODY;
            else
                logLevel = HttpLoggingInterceptor.Level.NONE;

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    // Enable response caching
                    .addNetworkInterceptor(new ResponseCacheInterceptor())
                    // Set the cache location and size (5 MB)
                    .cache(new Cache(new File(MarsExplorerApplication.getApplicationInstance()
                            .getCacheDir(), RestClientConstants.apiResponsesCache),
                            5 * 1024 * 1024))
                    // Enable logging
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(logLevel))
                    .build();

            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                    new GsonBuilder().registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                            .create()
            );

            Retrofit retrofitClient = new Retrofit.Builder()
                    .baseUrl(RestClientConstants.marsWeatherBaseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            marsWeatherInterface = retrofitClient.create(MarsWeatherInterface.class);
        }

        return marsWeatherInterface;
    }

    public interface MarsWeatherInterface {

        @GET("api.php")
        Observable<MarsWeatherResultDM> getLatestMarsWeather(
                @Header(RestClientConstants.responseCachingFlagHeader) boolean responseCacheFlag
        );
    }
}
