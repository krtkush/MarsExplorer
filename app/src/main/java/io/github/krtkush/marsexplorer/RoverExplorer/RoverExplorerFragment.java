package io.github.krtkush.marsexplorer.RoverExplorer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

/**
 * Created by kartikeykushwaha on 01/09/16.
 */
public class RoverExplorerFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.common_recyclerview_layout, container, false);

        Timber.tag(getActivity().getClass().getSimpleName());

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
