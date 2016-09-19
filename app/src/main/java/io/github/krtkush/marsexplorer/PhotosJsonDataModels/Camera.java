package io.github.krtkush.marsexplorer.PhotosJsonDataModels;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeykushwaha on 19/09/16.
 */
@AutoValue
public abstract class Camera {

    @SerializedName("id")
    public abstract Integer id();
    @SerializedName("name")
    public abstract String name();
    @SerializedName("rover_id")
    public abstract Integer roverId();
    @SerializedName("full_name")
    public abstract String cameraFullName();

    public static TypeAdapter<Camera> typeAdapter(Gson gson) {
        return new AutoValue_Camera.GsonTypeAdapter(gson);
    }
}
