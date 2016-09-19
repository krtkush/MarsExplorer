package io.github.krtkush.marsexplorer.PicturesJsonDataModels;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeykushwaha on 19/09/16.
 */
@AutoValue
public abstract class Rover {

    @SerializedName("id")
    public abstract Integer id();
    @SerializedName("name")
    public abstract String name();
    @SerializedName("max_sol")
    public abstract Integer maxSol();

    public static TypeAdapter<Rover> typeAdapter(Gson gson) {
        return new AutoValue_Rover.GsonTypeAdapter(gson);
    }
}
