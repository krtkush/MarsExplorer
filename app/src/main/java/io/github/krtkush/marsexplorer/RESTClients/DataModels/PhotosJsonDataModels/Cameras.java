package io.github.krtkush.marsexplorer.RESTClients.DataModels.PhotosJsonDataModels;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeykushwaha on 19/09/16.
 */
@AutoValue
public abstract class Cameras {

    /**
     * List of all Cameras onboard the rover.
     */

    @SerializedName("name")
    public abstract String cameraShortName();

    @SerializedName("full_name")
    public abstract String cameraFullName();

    public static TypeAdapter<Cameras> typeAdapter(Gson gson) {
        return new AutoValue_Cameras.GsonTypeAdapter(gson);
    }
}
