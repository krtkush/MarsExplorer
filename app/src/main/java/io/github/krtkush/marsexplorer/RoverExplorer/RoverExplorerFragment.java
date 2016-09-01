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
    
    public static RoverExplorerFragment newInstance(String sol, String roverName) {
        
        Bundle args = new Bundle();
        RoverExplorerFragment fragment = new RoverExplorerFragment();

        args.putString("sol", sol);
        args.putString("roverName", roverName);
        fragment.setArguments(args);
        return fragment;
    }

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

        Bundle args = getArguments();
        int testVal = args.getInt("sol");
        Timber.i("Sol: %s", String.valueOf(testVal));
        Timber.i("Fragment created");

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
