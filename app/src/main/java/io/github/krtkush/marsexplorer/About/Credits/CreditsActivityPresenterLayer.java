package io.github.krtkush.marsexplorer.About.Credits;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.R;
import io.github.krtkush.marsexplorer.UtilityMethods;

/**
 * Created by kartikeykushwaha on 22/10/16.
 */

public class CreditsActivityPresenterLayer implements CreditsActivityPresenterInteractor{

    private CreditsActivity activity;

    public CreditsActivityPresenterLayer(CreditsActivity activity) {
        this.activity = activity;
    }

    @Override
    public void checkInternetConnectivity() {
        if(!UtilityMethods.isNetworkAvailable())
            activity.showToast(activity.getResources()
                            .getString(R.string.no_internet),
                    Toast.LENGTH_LONG);
    }

    @Override
    public void prepareRecyclerViewAndAddData(RecyclerView recyclerView) {

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        CreditsRecyclerViewAdapter creditsRecyclerViewAdapter =
                new CreditsRecyclerViewAdapter(activity, populateCreditsDsWithValues());

        recyclerView.setAdapter(creditsRecyclerViewAdapter);
    }

    /**
     * Method to populate the
     * @return
     */
    private CreditsListDataStructure populateCreditsDsWithValues() {

        CreditsListDataStructure creditsListDataStructure = new CreditsListDataStructure();
        SparseArray<String> titleList = new SparseArray<>();
        SparseArray<String> subTitle = new SparseArray<>();

        titleList.put(0, "Retrofit");
        subTitle.put(0, "https://square.github.io/retrofit/");

        titleList.put(1, "OkHttp");
        subTitle.put(1, "http://square.github.io/okhttp/");

        titleList.put(2, "Butter Knife");
        subTitle.put(2, "http://jakewharton.github.io/butterknife/");

        titleList.put(3, "RxAndroid");
        subTitle.put(3, "https://github.com/ReactiveX/RxAndroid");

        titleList.put(4, "RxJava");
        subTitle.put(4, "https://github.com/ReactiveX/RxJava");

        titleList.put(5, "Timber");
        subTitle.put(5, "https://github.com/JakeWharton/timber");

        titleList.put(6, "Calligraphy");
        subTitle.put(6, "https://github.com/chrisjenx/Calligraphy");

        titleList.put(7, "Picasso");
        subTitle.put(7, "http://square.github.io/picasso/");

        titleList.put(8, "Leak Canary");
        subTitle.put(8, "https://github.com/square/leakcanary");

        titleList.put(9, "AutoValue");
        subTitle.put(9, "https://github.com/google/auto/tree/master/value");

        titleList.put(10, "Github icon by Dave Gandy");
        subTitle.put(10, "http://www.flaticon.com/authors/dave-gandy");

        creditsListDataStructure.setTitle(titleList);
        creditsListDataStructure.setSubTitle(subTitle);

        return creditsListDataStructure;
    }
}
