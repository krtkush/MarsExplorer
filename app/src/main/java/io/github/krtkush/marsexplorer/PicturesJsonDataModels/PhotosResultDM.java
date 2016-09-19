package io.github.krtkush.marsexplorer.PicturesJsonDataModels;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeykushwaha on 16/09/16.
 */
@AutoValue
public abstract class PhotosResultDM {

    @SerializedName("photos")
    public abstract List<Photos> photos();

    public static TypeAdapter<PhotosResultDM> typeAdapter(Gson gson) {
        return new AutoValue_PhotosResultDM.GsonTypeAdapter(gson);
    }
}
