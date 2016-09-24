package io.github.krtkush.marsexplorer.WeatherJsonDataModel;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeykushwaha on 24/09/16.
 */
@AutoValue
public abstract class MarsWeatherResultDM {

    /**
     * List of the weather report for every SOL
     */

    @SerializedName("soles")
    public abstract List<Soles> weatherReportList();

    public static TypeAdapter<MarsWeatherResultDM> typeAdapter(Gson gson) {
        return new AutoValue_MarsWeatherResultDM.GsonTypeAdapter(gson);
    }
}
