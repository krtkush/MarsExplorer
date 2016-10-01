package io.github.krtkush.marsexplorer.RESTClients.DataModels.WeatherJsonDataModel;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeykushwaha on 24/09/16.
 */
@AutoValue
public abstract class Soles {

    /**
     * Weather details of a particular SOL
     */

    @SerializedName("id")
    public abstract String id();

    @SerializedName("sol")
    public abstract String sol();

    @SerializedName("season")
    public abstract String season();

    @SerializedName("min_temp")
    public abstract String minTemp();

    @SerializedName("max_temp")
    public abstract String maxTemp();

    @SerializedName("pressure")
    public abstract String pressure();

    @SerializedName("pressure_string")
    public abstract String pressureString();

    @SerializedName("atmo_opacity")
    public abstract String atmosphereOpacity();

    @SerializedName("sunrise")
    public abstract String sunriseTime();

    @SerializedName("sunset")
    public abstract String sunsetTime();

    @SerializedName("local_uv_irradiance_index")
    public abstract String uvIndex();

    public static TypeAdapter<Soles> typeAdapter(Gson gson) {
        return new AutoValue_Soles.GsonTypeAdapter(gson);
    }
}
