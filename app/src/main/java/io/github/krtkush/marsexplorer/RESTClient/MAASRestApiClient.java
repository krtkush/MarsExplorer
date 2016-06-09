package io.github.krtkush.marsexplorer.RESTClient;

import java.io.File;

import io.github.krtkush.marsexplorer.MarsExplorer;
import io.github.krtkush.marsexplorer.WeatherJsonDataModel.MarsWeatherDM;
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
public class MAASRestApiClient {

    private static MAASWeatherApiInterface maasWeatherApiInterface;

    public static MAASWeatherApiInterface getMaasWeatherApiInterface() {

        if(maasWeatherApiInterface == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    // Enable response caching
                    .addNetworkInterceptor(new ResponseCacheInterceptor())
                    // Set the cache location and size (5 MB)
                    .cache(new Cache(new File(MarsExplorer.getApplicationInstance().getCacheDir(),
                            "apiResponses"), 5 * 1024 * 1024))
                    // Enable logging
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            Retrofit retrofitClient = new Retrofit.Builder()
                    .baseUrl(RestClientConstants.maasApiBaseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            maasWeatherApiInterface = retrofitClient.create(MAASWeatherApiInterface.class);
        }

        return maasWeatherApiInterface;
    }

    public interface MAASWeatherApiInterface {

        @GET("latest")
        Observable<MarsWeatherDM> getLatestMarsWeather(
                @Header(RestClientConstants.responseCachingFlagHeader) boolean responseCacheFlag
        );
    }
}
