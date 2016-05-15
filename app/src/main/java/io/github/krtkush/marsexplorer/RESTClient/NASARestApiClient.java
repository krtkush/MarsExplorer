package io.github.krtkush.marsexplorer.RESTClient;

import java.io.IOException;

import io.github.krtkush.marsexplorer.JsonDataModels.PhotoSearchResultDM;
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

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))               //Enable logging
                    .addInterceptor(new DefaultValuesInterceptor(Constants.apiKey))     //Add the api key by default
                    .build();

            Retrofit retrofitClient = new Retrofit.Builder()
                    .baseUrl(Constants.baseUrl)
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
}
