package io.github.krtkush.marsexplorer.RESTClients.DataModels.PhotosJsonDataModels;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeykushwaha on 18/09/16.
 */
@AutoValue
public abstract class Photos {

    /**
     *Details of the image taken by the rover
     */

    @SerializedName("id")
    public abstract Integer id();

    @SerializedName("sol")
    public abstract Integer sol();

    @SerializedName("img_src")
    public abstract String imgSource();

    @SerializedName("earth_date")
    public abstract String earthDate();

    @SerializedName("rover")
    public abstract Rover rover();

    @SerializedName("camera")
    public abstract Camera camera();

    public static TypeAdapter<Photos> typeAdapter(Gson gson) {
        return new AutoValue_Photos.GsonTypeAdapter(gson);
    }
}
