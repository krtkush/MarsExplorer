package io.github.krtkush.marsexplorer.RoverExplorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;

public class RoverExplorer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_explorer);

        ButterKnife.bind(this);
    }
}
